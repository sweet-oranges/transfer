package com.brilliant.lf.controller;

import com.brilliant.lf.bean.Link;
import com.brilliant.lf.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @Author zxl on 2019/8/13
 */
@RestController
@CrossOrigin
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private LinkService linkService;


    /**
     * 根据端口获取所有主题
     * @param port
     * @return
     */
    @RequestMapping("/getAll")
    public List<Link> getTopicByPort(String port){

        System.out.println(linkService.getTopicByPort(port));
        return linkService.getTopicByPort(port);
    }

    @RequestMapping("/add")
    public void addTopic(String port,String topic){
        Link link=new Link();
        link.setPort(port);
        link.setId(linkService.getMaxId()+1);
        link.setTopic(topic);
        linkService.insertSelective(link);
    }


}