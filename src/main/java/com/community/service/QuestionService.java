package com.community.service;

import com.community.dto.QuestionDTO;
import com.community.exception.CustomErrorCodeEnumImp;
import com.community.exception.CustomException;
import com.community.mapper.QuestionDTOMapper;
import com.community.mapper.QuestionExtMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.QuestionExample;
import com.community.model.User;
import com.community.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private QuestionExtMapper questionExtMapper;


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
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andIdEqualTo(question.getUserId());
            List<User> users = userMapper.selectByExample(userExample);
            User user = users.get(0);
            System.out.println("user = " + user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setList(questionDTOList);
        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return pageInfo;
    }

    public List<QuestionDTO> findList1() {
//        查出所有的question数据，放在list集合里面

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdIsNotNull();
        List<Question> questions = questionMapper.selectByExample(questionExample);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        System.out.println("questions = " + questions);
//        使用循环遍历，获取具体的question
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTOList.add(questionDTO);
        }

        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return questionDTOList;
    }

    public PageInfo<QuestionDTO> findMyList(Integer pageNum, Integer pageSize, Long userId) {
        //        1.判断页面参数非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        System.out.println("初始页面，当前位置为第" + pageNum + "页" + "   分页范围:展示" + pageSize + "页");
//        2引入分页插件，pageNum为第几页，pageSize为分页展示页面总数，count为查询总数
//        PageHelper.startPage(pageNum, pageSize);
        //        查出所有的question数据，放在list集合里面
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionDTO> questions = questionDTOMapper.queryMyQuestionDTO(userId);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questions);
        System.out.println("questions = " + questions);
//        使用循环遍历，获取具体的question
        for (QuestionDTO question : questions) {
//            questionDTO.setTitle(question.getTitle());
//            questionDTO.setDescription(question.getDescription());
//            questionDTO.setTag(question.getTag());
//            通过每个question的creator获取user
            UserExample userEexample = new UserExample();
            userEexample.createCriteria()
                    .andIdEqualTo(question.getUserId());
            List<User> users = userMapper.selectByExample(userEexample);
            User user = users.get(0);
            System.out.println("user = " + user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setList(questionDTOList);
        System.out.println("questionDTOList = " + questionDTOList);


//        questionDTO.setTitle();

        return pageInfo;
    }

    public QuestionDTO findQuestionById(Long id) {
        QuestionDTO question = questionDTOMapper.queryMyQuestionDTOById(id);
        if (question == null) {
            throw new CustomException(CustomErrorCodeEnumImp.QUESTION_NOT_FOUND);
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdEqualTo(question.getUserId());
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        QuestionDTO questionDTO = new QuestionDTO();
        String creator = question.getCreator();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdateQuestion(Question question) {
        if (question.getId() == null) {
//            创建问题
// Question newQuestion = new Question();
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionDTOMapper.insertQuestionDTO(question);
        } else {
            System.out.println("我要更新问题 = ");

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int codeStatus = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (codeStatus == 0) {
                throw new CustomException(CustomErrorCodeEnumImp.UPDATE_QUESTION_NOT_FOUND);
            }
        }

    }

    public void addViewCount(Long id) {
/*//        查出来当前question的viewCount是多少
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(id);
        List<Question> questions = questionMapper.selectByExample(questionExample);

        Question updateQuestion = new Question();
        Integer viewCount = questions.get(0).getViewCount();
//        int i = viewCount + 1;
//
//        System.out.println("i = " + i);
        updateQuestion.setViewCount(viewCount +1);

        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andIdEqualTo(questions.get(0).getId());
        questionMapper.updateByExampleSelective(updateQuestion, questionExample1);*/
//        *********************第二种方式************************************
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }

    /**
     * 热点关联问题查询实现方法
     * @param queryQuestionDTO
     * @return
     */
    public List<QuestionDTO> queryHotQuestion(QuestionDTO queryQuestionDTO) {
        if (StringUtils.isBlank(queryQuestionDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryQuestionDTO.getTag(), ",| ");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryQuestionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.queryHotQuestion(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
