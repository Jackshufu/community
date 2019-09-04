package com.community.service;

import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionDTOMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Autowired
    private QuestionDTOMapper questionDTOMapper;

//    @Autowired
//    private QuestionDTO questionDTO;


    public PageInfo<QuestionDTO> findList(Integer pageNum, Integer pageSize) {
//        查出所有的question数据，放在list集合里面
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionDTO> questions = questionDTOMapper.queryQuestionDTO();
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questions);
        System.out.println("questions = " + questions);

//        使用循环遍历，获取具体的question
        for (QuestionDTO question : questions) {
//            questionDTO.setTitle(question.getTitle());
//            questionDTO.setDescription(question.getDescription());
//            questionDTO.setTag(question.getTag());
//            通过每个question的creator获取user
            User user = userMapper.findUserById(question.getUserId());
            System.out.println("user = " + user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setList(questionDTOList);
        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return  pageInfo;
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

    public PageInfo<QuestionDTO> findMyList(Integer pageNum, Integer pageSize,String accountId) {
        //        查出所有的question数据，放在list集合里面
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionDTO> questions = questionDTOMapper.queryMyQuestionDTO(accountId);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questions);
        System.out.println("questions = " + questions);
//        使用循环遍历，获取具体的question
        for (QuestionDTO question : questions) {
//            questionDTO.setTitle(question.getTitle());
//            questionDTO.setDescription(question.getDescription());
//            questionDTO.setTag(question.getTag());
//            通过每个question的creator获取user
            User user = userMapper.findUserById(question.getUserId());
            System.out.println("user = " + user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setList(questionDTOList);
        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return  pageInfo;
    }

    public QuestionDTO findQuestionById(Integer id) {
        QuestionDTO question = questionDTOMapper.queryMyQuestionDTOById(id);
        User user = userMapper.findUserById(question.getUserId());
        QuestionDTO questionDTO = new QuestionDTO();
        String creator = question.getCreator();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdateQuestion(Question question) {
        if(question.getId() == null){
//            Question newQuestion = new Question();
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmt_modified(question.getGmtCreate());
            questionDTOMapper.insertQuestionDTO(question);
        }else{
            System.out.println("我要更新问题 = " );
            question.setGmt_modified(System.currentTimeMillis());
            questionMapper.updateQuestion(question);
        }

    }
}
