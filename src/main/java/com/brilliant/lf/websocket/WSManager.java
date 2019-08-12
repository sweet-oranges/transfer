package com.brilliant.lf.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * wsm
 *
 * @Author zxl on 2019/8/12
 */
@Slf4j
@Component
public class WSManager implements WebSocketManager {

    private ArrayList<WebSocketNode> clientList;

    public WSManager() {
        clientList = new ArrayList<WebSocketNode>();
        log.info("WSManager initialized");
    }

    public void addClient(WebSocketNode node) {
        clientList.add(node);
        log.debug("new client in");
    }

    public void removeClient(WebSocketNode node) {
        clientList.remove(node);
        log.debug("client removed");
    }

    public void publish(String data) {
        int size = clientList.size();
        log.debug("send to {} clients.", size);
        for (int i = 0; i < size; i++) {
            try {
                clientList.get(i).publish(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void publish(byte[] data) {
        int size = clientList.size();
        log.debug("send to {} clients.", size);
        for (int i = 0; i < size; i++) {
            try {
                clientList.get(i).publish(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}