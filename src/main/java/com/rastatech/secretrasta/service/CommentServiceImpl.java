package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.CommentRequest;
import com.rastatech.secretrasta.dto.response.CommentResponse;
import com.rastatech.secretrasta.dto.request.UpdateCommentRequest;
import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.repository.CommentRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentEntity createComment(Long userId, Long wishId, CommentRequest comment) {
        CommentEntity commentEntity = new CommentEntity();
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        commentEntity.setComment(comment.getComment());
        commentEntity.setWish(wish);
        commentEntity.setUser(user);
        return commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentEntity> fetchCommentsByWish(Long wishId) {
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return commentRepository.findByWish(wish);
    }

    @Override
    public List<CommentEntity> fetchCommentsByUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return commentRepository.findByUser(user);
    }

    @Override
    public void updateComment(Long wishId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(updateCommentRequest, comment);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long wishId, Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentEntity fetchComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    private CommentResponse mapToCommentResponse(CommentEntity comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }
}
