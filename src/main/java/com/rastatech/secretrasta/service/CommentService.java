package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.CommentRequest;
import com.rastatech.secretrasta.dto.request.UpdateCommentRequest;
import com.rastatech.secretrasta.model.CommentEntity;

import java.util.List;

public interface CommentService {
    CommentEntity createComment(Long userId, Long wishId, CommentRequest comment);
    List<CommentEntity> fetchCommentsByWish(Long wishId);
    List<CommentEntity> fetchCommentsByUser(Long userId);
    void updateComment(Long wishId, Long commentId, UpdateCommentRequest comment);
    void deleteComment(Long wishId, Long commentId);
    CommentEntity fetchComment(Long commentId);
}
