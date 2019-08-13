package com.brilliant.lf.websocket;

import com.brilliant.lf.bean.Link;
import com.brilliant.lf.service.LinkService;
import com.brilliant.lf.socket.TCPReceiver;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * wsn
 *
 * @Author zxl on 2019/8/12
 */

@Slf4j
@Component
@ServerEndpoint(value = "/devMessage")
public class WebSocketNode extends TCPReceiver {
//    private Session session;
//    private WebSocketManager manager;
//    public static String ms;
//    public static Map<String,String> topics = new HashMap<>();
//    private Map<String,ServerSocket> map = new HashMap<>();
////    private ArrayList<WebSocketNode> clientList;
//
//    @Autowired
//    public WebSocketNode(WebSocketManager webSocketManager){
//        this.manager = webSocketManager;
////        clientList = new ArrayList<WebSocketNode>();
//    }
//
//    @Autowired
//    private LinkService linkService;
//
//
//    @OnMessage
//    public void onMessage(String message,Session session) throws IOException,InterruptedException{
//
//        List<Link> list = linkService.getAll();
//        System.out.println("从数据库拿来的数据"+list);
//
//        if(log.isDebugEnabled()){
//
//            JSONObject jsonObject = JSONObject.fromObject(message);
//            System.out.println(jsonObject.get("text"));
//
//            String flag = jsonObject.getString("flag");
//
//            if(flag.equals("m")){
//                byte[] bytes = String2Byte(jsonObject.getString("text"));
//                try{
//                    for(String s: TCPReceiver.map.keySet()){
//                        OutputStream os = TCPReceiver.map.get(s).getOutputStream();
//                        os.write(bytes);
//                        os.flush();
//                    }
//                }catch (SocketException e){
//                    e.printStackTrace();
//                }
//
//                log.debug(">" + message);
//            }else if(flag.equals("t")){
//                String topic = jsonObject.getString("text");
//                String port = jsonObject.getString("port");
//                topics.put(port,topic);
//            }else if(flag.equals("p")){
//                String port = jsonObject.getString("text");
//                ServerSocket serverSocket = startTCPServer(Integer.parseInt(port));
//                map.put(port,serverSocket);
//                System.out.println(map.keySet());
//            }else if(flag.equals("d")){
//                try{
////                    System.out.println(message.substring(1));
////                    System.out.println(map.get(message.substring(1))+"map的值");
//                    String port = jsonObject.getString("text");
//                    map.get(port).close();
//                    map.remove(port);
//                    System.out.println("释放成功");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
////        log.debug("Client connected");
////        manager.addClient(this);
////        clientList.add(node);
////        log.debug("new client in");
//    }
//
//    @OnClose
//    public void onClose() {
//        log.debug("Client closed");
////        manager.removeClient(this);
////        clientList.remove(node);
//
//    }
//
//
//    public void publish(String data) throws IOException {
//        this.session.getBasicRemote().sendText(data);
//    }
//
//
//    /**
//     * byte[]数组转换为16进制的字符串
//     *
//     * @param s 要转换的十六进制字符串
//     * @return 转换后的结果
//     */
//    public static byte[] String2Byte(String s) {
//        s = s.replace(" ", "");
//        s = s.replace("#", "");
//        byte[] baKeyword = new byte[s.length() / 2];
//        for (int i = 0; i < baKeyword.length; i++) {
//            try {
//                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return baKeyword;
//    }
//
//

}