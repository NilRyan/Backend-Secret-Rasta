//package com.rastatech.secretrasta.controller;
//
//import com.rastatech.secretrasta.dto.CommentResponse;
//import com.rastatech.secretrasta.model.CommentEntity;
//import com.rastatech.secretrasta.service.CommentService;
//import com.rastatech.secretrasta.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/users/{user_id}/comments")
//@RequiredArgsConstructor
//public class UserCommentsController {
//
//    private final ModelMapper modelMapper;
//    private final CommentService commentService;
//    private final UserService userService;
//
//    @GetMapping
//    public List<CommentResponse> fetchCommentsByUser(Authentication auth) {
//        String username = (String) auth.getPrincipal();
//        Long userId = userService.fetchUserByUsername(username).getUserId();
//        List<CommentEntity> comments = commentService.fetchCommentsByUser(userId);
//        return comments.stream().map(this::mapToCommentResponse).collect(Collectors.toList());
//    }
//
//    private CommentResponse mapToCommentResponse(CommentEntity comment) {
//        return modelMapper.map(comment, CommentResponse.class);
//    }
//
//}
