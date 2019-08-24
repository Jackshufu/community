package com.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by 舒先亮 on 2019/8/23.
 */
@Controller
public class PublishController {

    @GetMapping("publish")
    public String publish(){
        return "publish";
    }
}
