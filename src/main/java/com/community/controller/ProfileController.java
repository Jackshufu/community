package com.community.controller;

import com.community.dto.QuestionDTO;
import com.community.mapper.UserMapper;
import com.community.model.User;
import com.community.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 舒先亮 on 2019/8/29.
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("profile/{action}")
    public String getMyQuestion(@PathVariable(name = "action")String action,
                                HttpServletRequest request,
                                Model model,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
//        1.拦截器校验是否登录，没有登录则返回首页
        User userFoundByToken = (User) request.getSession().getAttribute("userFindByToken");

        if(userFoundByToken == null){
            model.addAttribute("error", "请登录查看我的问题列表");
            return "index";
        }


//        2.校验通过后，登录，然后判断传过来的action是什么，来返回对应的值
        System.out.println("进来了");
        if("questions".equals(action)){   //参数倒着写，防止空指针
            System.out.println("进来了equals");
            model.addAttribute("action", "questions");
            model.addAttribute("actionName", "我的问题");
            /**
             * 登录 后对question数据进行分页
             * */
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
            try {
                String accountId = userFoundByToken.getAccountId();
                PageInfo<QuestionDTO> myQuestions = questionService.findMyList(pageNum,pageSize,accountId);
                model.addAttribute("myQuestions", myQuestions);
                List<QuestionDTO> myQuestionsList = myQuestions.getList();
                System.out.println("myQuestionsList = " + myQuestionsList);
            /*for (QuestionDTO questionDTO : questionsList) {
                System.out.println("questionDTO = " + questionDTO);
            }*/
                model.addAttribute("myQuestionsList", myQuestionsList);

//            PageInfo<QuestionDTO> questionDTOPageInfo = new PageInfo<>(questions, pageSize);
//            System.out.println("questionDTOPageInfo = " + questionDTOPageInfo.getList());
//            model.addAttribute("questions", questions);
            } finally {
                PageHelper.clearPage();//清除ThreadLocal存储 的分页信息，保证线程安全
            }
        }
        if("replies".equals(action)){
            model.addAttribute("action", "replies");
            model.addAttribute("actionName", "我的回复");
        }
        return "profile";
    }
}
