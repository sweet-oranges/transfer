package com.brilliant.lf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主要控制类
 *
 * @Author zxl on 2019/8/12
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public ModelAndView test(){
        return new ModelAndView("index");
    }

    @RequestMapping("/go")
    public ModelAndView go(){
        return new ModelAndView("go");
    }
}