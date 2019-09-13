package com.community.controller;

import com.community.dto.CommentCreateDTO;
import com.community.dto.ResultDTO;
import com.community.exception.CustomErrorCodeEnumImp;
import com.community.model.Comment;
import com.community.model.User;
import com.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 舒先亮 on 2019/9/9.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object postComment(@RequestBody CommentCreateDTO commentCreateDTO,
                              HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("userFindByToken");
        if(user == null){
            return ResultDTO.errorOf(CustomErrorCodeEnumImp.NO_LOGIN);
        }
//        在判断评论内容是否为"" 或 null，我们用或者判断了两次;这里我们引入commons.lang3的包，使用isBlank()方法判断是否是以上的两种情况，比较方便
//        if(commentCreateDTO == null || commentCreateDTO.getContent() == "" || commentCreateDTO.getContent() == null){
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomErrorCodeEnumImp.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
