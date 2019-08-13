package com.brilliant.lf.websocket;

import com.brilliant.lf.socket.TCPReceiver;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author zxl on 2019/8/13
 */
@ServerEndpoint("/websocket")
@Component
public class WebSockTest extends TCPReceiver {

    private Session session;
    private Manager manager;
    private static int onlineCount=0;
    private static CopyOnWriteArrayList<WebSockTest> webSocketSet=new CopyOnWriteArrayList<WebSockTest>();
    public static Map<String,String> topics = new HashMap<>();
    private Map<String,ServerSocket> map = new HashMap<>();


//    @Autowired
//    public WebSockTest(Manager manager){
//        this.manager = manager;
//    }
    static {
        topics.put("9000","manhole_monitor_up");
}

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭!");
    }

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
//        manager.addClient(this);
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有一连接!");
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException, InterruptedException {
        System.out.println("来自客户端的消息："+message);
        JSONObject jsonObject =JSONObject.fromObject(message);
        System.out.println(jsonObject.get("text"));

        String flag = jsonObject.getString("flag");

        if(flag.equals("m")){
            byte[] bytes = String2Byte(jsonObject.getString("text"));
            try{
                for(String s: TCPReceiver.map.keySet()){
                    OutputStream os = TCPReceiver.map.get(s).getOutputStream();
                    os.write(bytes);
                    os.flush();
                }
            }catch (SocketException e){
                e.printStackTrace();
            }
        }else if(flag.equals("t")){
            String topic = jsonObject.getString("text");
            String port = jsonObject.getString("port");
            topics.put(topic,port);
        }else if(flag.equals("p")){
            String port = jsonObject.getString("text");
            ServerSocket serverSocket = startTCPServer(Integer.parseInt(port));
            map.put(port,serverSocket);
            System.out.println(map.keySet());
        }else if(flag.equals("d")){
            try{
                String port = jsonObject.getString("text");
                map.get(port).close();
                map.remove(port);
                System.out.println("释放成功");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }
    //   下面是自定义的一些方法
    public void publish(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        WebSockTest.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSockTest.onlineCount--;
    }

    public static void pushAll(String message){
        for (WebSockTest item:webSocketSet){
            try {
                item.publish(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param s 要转换的十六进制字符串
     * @return 转换后的结果
     */
    public static byte[] String2Byte(String s) {
        s = s.replace(" ", "");
        s = s.replace("#", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword;
    }
}