package com.community.mapper;

import com.community.model.Comment;
import com.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}