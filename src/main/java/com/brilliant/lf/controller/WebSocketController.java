package com.brilliant.lf.controller;

import java.io.IOException;

import com.brilliant.lf.websocket.WebSocketTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * da
 *
 * @Author zxl on 2019/8/12
 */
@RestController
@RequestMapping("/api/ws")
public class WebSocketController {
    @RequestMapping(value="/sendAll", method=RequestMethod.GET)
    /**
     * 群发消息内容
     * @param message
     * @return
     */
    String sendAllMessage(@RequestParam(required=true) String message){
        try {
            WebSocketTest.BroadCastInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
    @RequestMapping(value="/sendOne", method=RequestMethod.GET)
    /**
     * 指定会话ID发消息
     * @param message 消息内容
     * @param id 连接会话ID
     * @return
     */
    String sendOneMessage(@RequestParam(required=true) String message,@RequestParam(required=true) String id){
        try {
            WebSocketTest.SendMessage(id,message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}