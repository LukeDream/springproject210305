package com.luke.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName MainController
 * @Description TODO
 * @Author lulu
 * @Date 2020/11/19 15:58
 * @Version 1.0
 **/
@Controller
public class MainController {


    @RequestMapping("/")
    public String index(){

        return "index";
    }

    @RequestMapping("index")
    public String index1(){

        return "index";
    }
}
