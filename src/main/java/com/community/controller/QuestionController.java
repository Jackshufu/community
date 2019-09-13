package com.community.controller;

import com.community.dto.CommentDTO;
import com.community.dto.QuestionDTO;
import com.community.service.CommentService;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by 舒先亮 on 2019/9/1.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("question/{id}")
    public String getQuestion(@PathVariable(name = "id") Long id, Model model) {

        questionService.addViewCount(id);
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        List<CommentDTO> comments = commentService.queryListByQuestionId(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        return "question";
    }


}
