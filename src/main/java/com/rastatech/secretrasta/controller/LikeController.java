package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.service.LikeService;
import com.rastatech.secretrasta.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/wishes/{wish_id}/like")
    @ApiOperation(value = "Like or Unlike a wish",
            notes = "When the wish is not yet liked by the user, using this api will like the specific wish. " +
                    "If the wish is already liked by the user, using this api will unlike the wish")
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
