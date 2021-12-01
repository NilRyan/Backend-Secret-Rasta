package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.*;
import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.model.WishVoteEntity;
import com.rastatech.secretrasta.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes")
@RequiredArgsConstructor
public class WishCommentsController {

    private ModelMapper modelMapper;
    private CommentService commentService;

    @PostMapping("/{wish_id}/comments")
    public CommentResponse createComment(@PathVariable("user_id") Long userId,
                                         @PathVariable("wish_id") Long wishId,
                                         @Valid @RequestBody CommentRequest comment) {
        CommentEntity commentEntity = commentService.createComment(userId, wishId, comment);
        return mapToCommentResponse(commentEntity);
    }

    @GetMapping("/{wish_id}/comments")
    public List<CommentResponse> fetchCommentByWish(@PathVariable("wish_id") Long wishId) {
        List<CommentEntity> comments = commentService.fetchCommentsByWish(wishId);
        return comments.stream().map(this::mapToCommentResponse).collect(Collectors.toList());
    }

    @GetMapping("/{user_id}/comments")
    public List<CommentResponse> fetchCommentsByUser(@PathVariable("user_id") Long userId) {
        List<CommentEntity> comments = commentService.fetchCommentsByUser(userId);
        return comments.stream().map(this::mapToCommentResponse).collect(Collectors.toList());
    }

    @PutMapping("/{wish_id}/comments/{comment_id}")
    public void updateComment(@PathVariable("wish_id") Long wishId,
                              @PathVariable("comment_id") Long commentId,
                              @Valid @RequestBody UpdateCommentRequest comment) {
        commentService.updateComment(wishId, commentId, comment);
    }

    @DeleteMapping("/{wish_id}/comments/{comment_id}")
    public void deleteComment(@PathVariable("wish_id") Long wishId,
                              @PathVariable("comment_id") Long commentId) {
        commentService.deleteComment(wishId, commentId);
    }

    private CommentResponse mapToCommentResponse(CommentEntity comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }
}
