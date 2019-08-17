package com.brilliant.lf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主要控制类
 *
 * @Author zxl on 2019/8/12
 */
@Controller
public class MainController {

//    @RequestMapping("/")
//    public ModelAndView test(){
//        return new ModelAndView("dad");
//    }

//    @RequestMapping("/login")
//    public ModelAndView go(){
//        return new ModelAndView("login");
//    }

    @RequestMapping("/")
    public String demo(){
       return "login";
    }

    @RequestMapping("/logins")
    public String login(String user, String password){
        System.out.println(user+password);
        if(user.equals("admin")&password.equals("123456")){
            return "/go";
        }
        return "/go";
    }
}