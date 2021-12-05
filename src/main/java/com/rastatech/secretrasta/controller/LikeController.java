package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.LikeRepository;
import com.rastatech.secretrasta.service.LikeService;
import com.rastatech.secretrasta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/wishes/{wish_id}/like")
    public void like(@PathVariable("wish_id") Long wishId, Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        likeService.like(wishId, userId);
    }

//    @DeleteMapping("/like/{like_id}")
//    public void unlike(@PathVariable("like_id") Long likeId, Authentication auth) {
//        if (!auth.getPrincipal().equals(likeService.fetchLike(likeId).getUser().getUsername()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        likeService.unlike(likeId);
//    }

}
