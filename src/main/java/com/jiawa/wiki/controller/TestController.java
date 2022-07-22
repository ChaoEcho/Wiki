package com.jiawa.wiki.controller;

import com.jiawa.wiki.domain.Test;
import com.jiawa.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by echo on 2022/7/20 11:57
 */
@ApiIgnore
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/hello/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String hello(@PathVariable String id){
        List<Test> list = testService.list();
        return list.toString();
    }

}
