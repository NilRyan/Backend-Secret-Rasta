package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.CommentRequest;
import com.rastatech.secretrasta.dto.UpdateCommentRequest;
import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.repository.CommentRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private WishRepository wishRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public CommentEntity createComment(Long wishId, CommentRequest comment) {
        return null;
    }

    @Override
    public List<CommentEntity> fetchCommentsByWish(Long wishId) {
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return commentRepository.findByWishEntity(wish);
    }

    @Override
    public List<CommentEntity> fetchCommentsByUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return commentRepository.findByUserEntity(user);
    }

    @Override
    public void updateComment(Long commentId, UpdateCommentRequest updateCommentRequest) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(updateCommentRequest, comment);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
