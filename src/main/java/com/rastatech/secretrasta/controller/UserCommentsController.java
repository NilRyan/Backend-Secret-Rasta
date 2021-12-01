package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.CommentResponse;
import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{user_id}/comments")
public class UserCommentsController {

    private ModelMapper modelMapper;
    private CommentService commentService;

    @GetMapping
    public List<CommentResponse> fetchCommentsByUser(@PathVariable("user_id") Long userId) {
        List<CommentEntity> comments = commentService.fetchCommentsByUser(userId);
        return comments.stream().map(this::mapToCommentResponse).collect(Collectors.toList());
    }

    private CommentResponse mapToCommentResponse(CommentEntity comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }

}
