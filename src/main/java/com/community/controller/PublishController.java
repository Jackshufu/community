package com.community.controller;

import com.community.cache.tagCache;
import com.community.dto.QuestionDTO;
import com.community.model.Question;
import com.community.model.User;
import com.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 舒先亮 on 2019/8/23.
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService    questionService;

    @GetMapping("publish")
    public String publish(Model model) {
        model.addAttribute("tags", tagCache.getTages());
        return "publish";
    }

    @PostMapping("publish")
    public String doPublish(
            /**
             * 将 thymeleaf前台输入的数据参数 传到后台
             * */
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            Model model,
            HttpServletRequest request) {
        /**
         * 将参数放到Model中去
         * */
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tagCache.getTages());

        /**
         * 判断前端的三个参数的值是否为空，为空则报错，并返回发布问题页面
         * */
        if (title == null || title == "") {
            model.addAttribute("error", "问题标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充栏位不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "问题标签不能为空");
            return "publish";
        }

        String invalid = tagCache.filterInValid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签"+invalid);
            return "publish";
        }
        /**
         * 拦截器判断是否登录，没有登录则报错未登录，并返回发布页面，cookie是放在request里的，因此我们要申明request参数
         * */
        /*if(cookies == null){
            model.addAttribute("error", "该用户未登录");
            return "publish";
        }*/

        User foundUserByToken = (User) request.getSession().getAttribute("userFindByToken");
        if(foundUserByToken == null){
            model.addAttribute("error", "该用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(foundUserByToken.getAccountId());
        question.setUserId(foundUserByToken.getId());
        question.setId(id);

        questionService.createOrUpdateQuestion(question);
        return "redirect:/";
    }

    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.findQuestionById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", tagCache.getTages());
        return "publish";
    }
}
