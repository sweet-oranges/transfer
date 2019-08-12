package com.brilliant.lf.mqtt;

import com.brilliant.lf.websocket.WebSocketNode;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * mqtt推送类
 *
 * @Author zxl on 2019/8/12
 */
@Slf4j
@Component
public class MQTTClient {
    private Thread workThread;
//    public String topic = "manhole_monitor_up";



    @PostConstruct
    public void init(){
        log.info("MQTTClient initialized");
    }

    public void publisher(){
    }



    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null) {
            return defaultValue;
        }
        return rc;
    }

    public static void sendMessage(String message,String destination) {
        String user = env("APOLLO_USER", "admin");
        String password = env("APOLLO_PASSWORD", "password");
        String host = env("APOLLO_HOST", "localhost");//apollo服务器地址
        int port = 61613;//apollo端口号
        //String destination = new_topic;//topic
//        List<String> list = WebSocketNode.topics;
        Map<String,String> map = WebSocketNode.topics;
        Buffer msg = new UTF8Buffer(message);
        MQTT mqtt = new MQTT();//新建MQTT
        try {
            mqtt.setHost(host, port);
            mqtt.setUserName(user);
            mqtt.setPassword(password);

            FutureConnection connection = mqtt.futureConnection();
            connection.connect().await();
            UTF8Buffer topic = new UTF8Buffer(destination);
            connection.publish(topic, msg, QoS.AT_LEAST_ONCE, false);
            connection.disconnect().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // System.exit(0);
        }
    }


}