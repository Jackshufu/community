package com.community.service;

import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒先亮 on 2019/8/26.
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

//    @Autowired
//    private QuestionDTO questionDTO;


    public List<QuestionDTO> findList() {
//        查出所有的question数据，放在list集合里面
        List<Question> questions = questionMapper.queryQuestion();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        System.out.println("questions = " + questions);
//        使用循环遍历，获取具体的question
        for (Question question : questions) {
//            questionDTO.setTitle(question.getTitle());
//            questionDTO.setDescription(question.getDescription());
//            questionDTO.setTag(question.getTag());
//            通过每个question的creator获取user
            User user = userMapper.findUserById(question.getCreator());
            System.out.println("user = " + user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return  questionDTOList;
    }

    public List<QuestionDTO> findList1() {
//        查出所有的question数据，放在list集合里面
        List<Question> questions = questionMapper.queryQuestion();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        System.out.println("questions = " + questions);
//        使用循环遍历，获取具体的question
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return  questionDTOList;
    }
}
