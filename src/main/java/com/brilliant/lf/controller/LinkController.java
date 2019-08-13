package com.brilliant.lf.controller;

import com.brilliant.lf.bean.Link;
import com.brilliant.lf.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 端口控制类
 *
 * @Author zxl on 2019/8/13
 */
@RestController
@CrossOrigin
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 获取所有端口
     * @return
     */
    @RequestMapping("/getAll")
    public List<Link> getAll(){
        return linkService.getAll();
    }
}