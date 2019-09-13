package com.community.service;

import com.community.dto.CommentDTO;
import com.community.enums.CommentTypeEnum;
import com.community.exception.CustomErrorCodeEnumImp;
import com.community.exception.CustomException;
import com.community.mapper.CommentMapper;
import com.community.mapper.QuestionExtMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomException(CustomErrorCodeEnumImp.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomException(CustomErrorCodeEnumImp.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
//            回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomException(CustomErrorCodeEnumImp.NO_COMMENT);
            }
            commentMapper.insert(comment);
        } else {
//            回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomException(CustomErrorCodeEnumImp.QUESTION_NOT_FIND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> queryListByQuestionId(Long id) {

        CommentExample commentExample = new CommentExample();
//        查询评论类型为question的所有的comments
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
//        接下来就是通过查到的评论去查找user，这里的查找User和question中的实现不太一样，因为评论非常多的话，我们会查询出来很多重复的user，因此在这里使用
//        java8 的语法，lambda表达式
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
//        查询所有的评论者
//        List<Integer> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toList());
//        set不允许重复
//        获取去重的评论人
        Set<Long> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
//        注入UserMapper，拿到User
        ArrayList<Long> userIds = new ArrayList<>();
        userIds.addAll(commentator);

//        获取评论人并转换为map，因为map的时候获取user的话，一次就能获取到，大大降低了时间复杂度
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
//         通过拼接的Sql语句，拿到了users
        List<User> users = userMapper.selectByExample(userExample);
//        然后就是通过匹配comment和users的id是否相等来处理评论人和对应的内容
//        如果用两个for循环，则时间复杂度为n^2，不建议
//        我们将User做成一个Map，这样每次取就能取到数据了
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
//        comments也可以通过lambda转换一下，转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
