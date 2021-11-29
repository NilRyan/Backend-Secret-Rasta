package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateCommentRequest;
import com.rastatech.secretrasta.model.CommentEntity;

import java.util.List;

public interface CommentService {
    CommentEntity createComment(Long commentId);
    List<CommentEntity> fetchComments();
    CommentEntity fetchComment(Long commentId);
    void updateComment(Long commentId, UpdateCommentRequest comment);
    void deleteComment(Long commentId);
}
