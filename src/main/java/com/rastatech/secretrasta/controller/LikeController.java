package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private LikeService likeService;

    @PostMapping("/{wish_id}")
    public void like(@PathVariable("wish_id") Long wishId) {
        likeService.like(wishId);
    }

}
