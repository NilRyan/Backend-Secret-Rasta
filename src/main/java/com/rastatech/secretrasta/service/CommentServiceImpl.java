package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.CommentRequest;
import com.rastatech.secretrasta.dto.UpdateCommentRequest;
import com.rastatech.secretrasta.model.CommentEntity;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    @Override
    public CommentEntity createComment(Long wishId, CommentRequest comment) {
        return null;
    }

    @Override
    public List<CommentEntity> fetchCommentsByWish(Long wishId) {
        return null;
    }

    @Override
    public List<CommentEntity> fetchCommentsByUser(Long userId) {
        return null;
    }

    @Override
    public CommentEntity fetchCommentByWish(Long wishId, Long commentId) {
        return null;
    }

    @Override
    public CommentEntity fetchCommentByUser(Long userId, Long commentId) {
        return null;
    }

    @Override
    public void updateComment(Long wishId, Long commentId, UpdateCommentRequest comment) {

    }

    @Override
    public void deleteComment(Long wishId, Long commentId) {

    }
}
