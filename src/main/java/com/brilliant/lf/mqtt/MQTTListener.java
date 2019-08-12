package com.brilliant.lf.mqtt;


import com.brilliant.lf.socket.TCPReceiver;
import com.brilliant.lf.websocket.WebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.OutputStream;
import java.net.URISyntaxException;
/**
 * mqtt接收类
 *
 * @Author zxl on 2019/8/12
 */
@Slf4j
@Component
public class MQTTListener {

    @Autowired
    private WebSocketManager manager;

    private Thread workThread;
    String user = env("APOLLO_USER", "admin");
    String password = env("APOLLO_PASSWORD", "password");
    String host = env("APOLLO_HOST", "localhost");
    int port = Integer.parseInt(env("APOLLO_PORT", "61613"));
    final String destination = "manhole_monitor_up";

    @PostConstruct
    public void init(){
        startListener();
        log.info("MQTTListener initialized");
    }

    private void startListener(){
        workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MQTT mqtt = new MQTT();
                    mqtt.setHost(host, port);
                    mqtt.setUserName(user);
                    mqtt.setPassword(password);
                    mqtt.setKeepAlive((short) 30);

                    final CallbackConnection connection = mqtt.callbackConnection();

                    connection.listener(new Listener() {
                        public void onConnected() {
                        }

                        public void onDisconnected() {
                        }

                        public void onFailure(Throwable value) {
                            value.printStackTrace();
                            System.exit(-2);
                        }

                        public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
                            System.out.println(msg);
                            byte[] bytes = msg.toByteArray();
                            for(String s:TCPReceiver.map.keySet()){
                                try{
                                    OutputStream os = TCPReceiver.map.get(s).getOutputStream();
                                    os.write(bytes);
                                    os.flush();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                            ack.run();
                        }
                    });
                    connection.connect(new Callback<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Topic[] topics = { new Topic("manhole_monitor", QoS.AT_LEAST_ONCE) };
                            connection.subscribe(topics, new Callback<byte[]>() {
                                public void onSuccess(byte[] qoses) {
                                    System.out.println("connected...");
                                }

                                public void onFailure(Throwable value) {
                                    value.printStackTrace();
                                    System.exit(-2);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable value) {
                            value.printStackTrace();
                            System.exit(-2);
                        }
                    });
                    // Wait forever..
                    synchronized (MQTTListener.class) {
                        while (true) {
                            MQTTListener.class.wait();
                        }
                    }
                }catch (URISyntaxException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        workThread.start();
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null) {
            return defaultValue;
        }
        return rc;
    }
}