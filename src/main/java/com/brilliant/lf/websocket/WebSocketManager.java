package com.brilliant.lf.websocket;

import org.springframework.stereotype.Component;

@Component
public interface WebSocketManager {
    void addClient(WebSocketNode node);

    void removeClient(WebSocketNode node);

    void publish(String data);

    void publish(byte[] data);
}
