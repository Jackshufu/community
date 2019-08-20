package com.community.commucity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 舒先亮 on 2019/8/20.
 */
@Controller
public class CommucityController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
