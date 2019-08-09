package com.brilliant.learn_fream.controller;

import com.brilliant.learn_fream.bean.Person;
import com.brilliant.learn_fream.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学习springboot框架
 *
 * @Author zxl on 2019/8/9
 */
@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PersonService personService;


    @RequestMapping("/getAll")
    public List<Person> getAll(){
        System.out.println(personService.getAll());
        return personService.getAll();
    }
}