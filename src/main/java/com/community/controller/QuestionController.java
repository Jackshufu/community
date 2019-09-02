package com.community.controller;

import com.community.dto.QuestionDTO;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by 舒先亮 on 2019/9/1.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("question/{id}")
    public String getQuestion(@PathVariable(name = "id") Integer id,
                              Model model){

        QuestionDTO questionDTO = questionService.findQuestionById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
