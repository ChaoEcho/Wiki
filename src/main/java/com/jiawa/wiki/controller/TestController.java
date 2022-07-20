package com.jiawa.wiki.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by echo on 2022/7/20 11:57
 */
@RestController
public class TestController {

    @RequestMapping(value = "/hello/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String hello(@PathVariable String id){
        return "Hello World!"+id+"Hello Test";
    }

}
