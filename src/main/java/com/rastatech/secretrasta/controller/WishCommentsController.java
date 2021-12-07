//package com.rastatech.secretrasta.controller;
//
//import com.rastatech.secretrasta.dto.CommentRequest;
//import com.rastatech.secretrasta.dto.CommentResponse;
//import com.rastatech.secretrasta.dto.UpdateCommentRequest;
//import com.rastatech.secretrasta.model.CommentEntity;
//import com.rastatech.secretrasta.service.CommentService;
//import com.rastatech.secretrasta.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/wishes")
//@RequiredArgsConstructor
//public class WishCommentsController {
//
//    private final ModelMapper modelMapper;
//    private final CommentService commentService;
//    private final UserService userService;
//
//    @PostMapping("/{wish_id}/comments")
//    public CommentResponse createComment(Authentication auth,
//                                         @PathVariable("wish_id") Long wishId,
//                                         @Valid @RequestBody CommentRequest comment) {
//        String username = (String) auth.getPrincipal();
//        Long userId = userService.fetchUserByUsername(username).getUserId();
//        CommentEntity commentEntity = commentService.createComment(userId, wishId, comment);
//        return mapToCommentResponse(commentEntity);
//    }
//
//    @GetMapping("/{wish_id}/comments")
//    public List<CommentResponse> fetchCommentByWish(@PathVariable("wish_id") Long wishId) {
//        List<CommentEntity> comments = commentService.fetchCommentsByWish(wishId);
//        return comments.stream().map(this::mapToCommentResponse).collect(Collectors.toList());
//    }
//
//    @GetMapping("/{user_id}/comments")
//    public List<CommentResponse> fetchCommentsByUser(@PathVariable("user_id") Long userId) {
//        List<CommentEntity> comments = commentService.fetchCommentsByUser(userId);
//        return comments.stream().map(this::mapToCommentResponse).collect(Collectors.toList());
//    }
//
//    @PutMapping("/{wish_id}/comments/{comment_id}")
//    public void updateComment(@PathVariable("wish_id") Long wishId,
//                              @PathVariable("comment_id") Long commentId,
//                              @Valid @RequestBody UpdateCommentRequest comment,
//                              Authentication auth) {
//        if (!auth.getPrincipal().equals(commentService.fetchComment(commentId).getUser().getUsername()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        commentService.updateComment(wishId, commentId, comment);
//    }
//
//    @DeleteMapping("/{wish_id}/comments/{comment_id}")
//    public void deleteComment(@PathVariable("wish_id") Long wishId,
//                              @PathVariable("comment_id") Long commentId,
//                              Authentication auth) {
//        if (!auth.getPrincipal().equals(commentService.fetchComment(commentId).getUser().getUsername()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        commentService.deleteComment(wishId, commentId);
//    }
//
//    private CommentResponse mapToCommentResponse(CommentEntity comment) {
//        return modelMapper.map(comment, CommentResponse.class);
//    }
//}
