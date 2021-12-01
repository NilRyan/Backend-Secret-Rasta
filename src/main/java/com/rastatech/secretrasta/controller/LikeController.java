package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.service.LikeService;
import com.rastatech.secretrasta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{wish_id}")
    public void like(@PathVariable("wish_id") Long wishId, Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        likeService.like(wishId, userId);
    }

    @DeleteMapping("/{like_id}")
    public void unlike(@PathVariable("like_id") Long likeId) {
        likeService.unlike(likeId);
    }

}
