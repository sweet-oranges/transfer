package com.brilliant.lf.controller;

import com.brilliant.lf.bean.Link;
import com.brilliant.lf.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/data")
public class DataController {

    @Autowired
    private LinkService linkService;

    /**
     * 修改传输方式
     * @param id
     * @param dataflag
     */
    @RequestMapping("dataType")
    public void updateDataType(int id,String dataflag){
        System.out.println(id+dataflag);
        linkService.updateDataFlag(id,dataflag);
    }
}
