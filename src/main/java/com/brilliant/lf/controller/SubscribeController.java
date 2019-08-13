package com.brilliant.lf.controller;

import com.brilliant.lf.bean.Subscribe;
import com.brilliant.lf.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @RequestMapping("/getAll")
    public List<Subscribe> getAll(){
        return subscribeService.getAll();
    }

    @RequestMapping("/delSubscribe")
    public void del(int id){
        subscribeService.deleteSubscribeById(id);
    }

    @RequestMapping("/addSubscribe")
    public void add(String port,String subscribe){
        Subscribe subscribes = new Subscribe();
        subscribes.setPort(port);
        subscribes.setSubscribe(subscribe);
        subscribes.setSid(subscribeService.getMaxId()+1);
        subscribeService.insertSelective(subscribes);
    }

    @RequestMapping("/getSub")
    public List<Subscribe> getSubscribeByPort(String port){
        return subscribeService.getSubscribeByPort(port);
    }
}
