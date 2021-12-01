package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private LikeService likeService;

    @PostMapping("/{wish_id}")
    public void like(@PathVariable("wish_id") Long wishId,
                                  @PathVariable("user_id") Long userId) {
        likeService.like(wishId, userId);
    }

    @DeleteMapping("/{like_id}")
    public void unlike(@PathVariable("like_id") Long likeId) {
        likeService.unlike(likeId);
    }

}
