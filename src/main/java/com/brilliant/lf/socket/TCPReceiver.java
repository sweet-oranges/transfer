package com.brilliant.lf.socket;

import com.brilliant.lf.bean.Link;
import com.brilliant.lf.service.LinkService;
import com.brilliant.lf.websocket.Manager;
import com.brilliant.lf.websocket.WebSockTest;
import com.brilliant.lf.websocket.WebSocketManager;
import com.brilliant.lf.websocket.WebSocketNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.brilliant.lf.mqtt.MQTTClient.sendMessage;


@Slf4j
@Component
public class TCPReceiver {

    private ServerSocket serverSocket;
    private Thread workThread;
    private boolean goRun;
    private static int count;
    private static String curr_port;
    public static Map<String, Socket> map = new ConcurrentHashMap<>();
    public static Map<String, String> ip_address = new HashMap<>();
    public String curr_socketip = "";
    //线程池
    private ExecutorService threadPool;

    @Autowired
    private LinkService linkService;


    @PostConstruct
    private void init() {
        System.out.println("初始化执行了");
        startTCPServer(9000);
        log.info("TCPReceiver initialized");
    }

    public TCPReceiver() {
        try {
            threadPool = Executors.newFixedThreadPool(25);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @PreDestroy
    public void shutdown() {
        stopTCPServer();
    }

    public ServerSocket startTCPServer(int port) {
        curr_port = String.valueOf(port);
        goRun = true;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetSocketAddress socketAddress = new InetSocketAddress("localhost", port);
                    System.out.println(socketAddress.toString());
                    threadPool = Executors.newFixedThreadPool(25);
                } catch (Exception e) {
                    log.error("ServerSocket", e);
                }
                System.out.println("等待客户端连接");
                Socket socket = null;
                try {
                    while (true) {
                        //创建Socket对象，并绑定server
                        socket = serverSocket.accept();
                        System.out.println("远程客户端主机地址为：" + socket.getRemoteSocketAddress());
                        curr_socketip = String.valueOf(socket.getInetAddress());
                        map.put(String.valueOf(socket.getInetAddress()) + String.valueOf(socket.getPort()), socket);
                        System.out.println("客户端" + socket.getInetAddress().getCanonicalHostName() + "已连接！");
                        //创建输入流。读取客户端传过来的数据
                        ClientHandler handler = new ClientHandler(socket);
                        threadPool.execute(handler);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        workThread.start();
        return serverSocket;
    }


    public void stopTCPServer() {
        goRun = false;
        try {
            workThread.join(10000);
        } catch (InterruptedException e) {
            log.error("tcp thread join", e);
        }
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 处理多客户端的内部类
     */
    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            PrintWriter pw = null;
            try {
                System.out.println("执行输出程序");
                BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                DataInputStream dis = new DataInputStream(bis);
                byte[] bytes = new byte[1]; //一次读取一个byte
                byte[] byte1 = new byte[]{};
                String ret = "";
                int i = 0;
                String original = "";
                while (dis.read(bytes) != -1) {
                    byte[] byte2 = new byte[i + 1];
                    System.arraycopy(byte1, 0, byte2, 0, byte1.length);
                    System.arraycopy(bytes, 0, byte2, byte1.length, bytes.length);
                    byte1 = byte2;
                    i++;
                    ret += bytesToHexString(bytes) + " ";
                    if (dis.available() == 0) { //一个请求
                        if (!ip_address.containsKey(curr_socketip)) {
                            ip_address.put(curr_socketip, String.valueOf(socket.getPort()));
                        }
                        List<Link> list = linkService.getTopicByPort(String.valueOf(serverSocket.getLocalPort()));
                        original = new String(byte1);
                        System.out.println(socket.getRemoteSocketAddress() + ":" + ret);
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = sdf.format(new Date());
                            if (String.valueOf(socket.getPort()).equals(ip_address.get(curr_socketip))) {
                                WebSockTest.pushAll(time + "," + ret);
                                for (int j = 0; j < list.size(); j++) {
                                    if (list.get(j).getDataflag().equals("0")) {
                                        sendMessage(ret, list.get(j).getTopic());
                                    } else {
                                        sendMessage(original, list.get(j).getTopic());
                                    }

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ret = "";
                        original = "";
                        byte1 = new byte[]{};
                        i = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        map.remove(String.valueOf(socket.getInetAddress()) + String.valueOf(socket.getPort()));
                        if (ip_address.containsKey(curr_socketip)) {
                            ip_address.remove(curr_socketip);
                        }
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
