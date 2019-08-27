package com.community.controller;

import com.community.dto.QuestionDTO;
import com.community.mapper.UserMapper;
import com.community.model.User;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 舒先亮 on 2019/8/20.
 */
@Controller
public class CommucityController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
//        当访问首页的时候，先获取请求中的cookies
        Cookie[] cookies = request.getCookies();
        System.out.println("11111111111133");
        /*if(cookies == null){
            return "index";
        }else*/
        if(cookies != null){
//         遍历cookies，查找键为token的session,如果某一个cookie的键等于token，则获取它的值，为session
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
//                这个token是数据库中存的值
                    String token = cookie.getValue();
                    System.out.println(" 数据库中找到和token相等的了 ");
//                通过找到数据库中存的token，再通过它查找user的全部信息
                    User userFindByToken = userMapper.findUserByToken(token);
                    if(userFindByToken != null){

                        request.getSession().setAttribute("userFindByToken",userFindByToken);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questions = questionService.findList();
        model.addAttribute("questions", questions);
        return "index";
    }
}
