package com.community.service;

import com.community.enums.CommentTypeEnum;
import com.community.exception.CustomErrorCodeEnumImp;
import com.community.exception.CustomException;
import com.community.mapper.CommentMapper;
import com.community.mapper.QuestionExtMapper;
import com.community.mapper.QuestionMapper;
import com.community.model.Comment;
import com.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒先亮 on 2019/9/9.
 */
@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomException(CustomErrorCodeEnumImp.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())){
            throw new CustomException(CustomErrorCodeEnumImp.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
//            回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomException(CustomErrorCodeEnumImp.NO_COMMENT);
            }
            commentMapper.insert(comment);
        }else{
//            回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomException(CustomErrorCodeEnumImp.QUESTION_NOT_FIND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
