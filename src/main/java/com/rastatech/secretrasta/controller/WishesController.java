package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.*;
import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.service.WishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes")
@RequiredArgsConstructor
public class WishesController {

    private final ModelMapper modelMapper;
    private final WishService wishService;

    @PostMapping
    public WishResponse createWish(@PathVariable("user_id") Long userId,
                                   @Valid @RequestBody WishRequest wishRequest) {
        WishEntity wish = wishService.createWish(userId, wishRequest);
        return mapToWishResponse(wish);
    }

    @GetMapping
    public List<WishResponse> fetchWishes(@PathVariable("user_id") Long userId) {
        List<WishEntity> wishes = wishService.fetchWishes();
        return wishes.stream().map(this::mapToWishResponse).collect(Collectors.toList());
    }

    @GetMapping("/{user_id}")
    public List<WishResponse> fetchWishesByUser(@PathVariable("user_id") Long userId) {
        List<WishEntity> wishes = wishService.fetchWishesByUser(userId);
        return wishes.stream().map(this::mapToWishResponse).collect(Collectors.toList());
    }

    @GetMapping("/{wish_id}")
    public WishResponse fetchWish(@PathVariable("wish_id") Long wishId) {
        WishEntity wish = wishService.fetchWish(wishId);
        return mapToWishResponse(wish);
    }

    @PutMapping("/{wish_id}")
    public void updateWish(@PathVariable("wish_id") Long wishId,
                              @Valid @RequestBody UpdateWishRequest wish) {
        wishService.updateWish(wishId, wish);
    }

    @DeleteMapping("/{wish_id}")
    public void deleteWish(@PathVariable("wish_id") Long wishId) {
        wishService.deleteWish(wishId);
    }

    private WishResponse mapToWishResponse(WishEntity wish) {
        return modelMapper.map(wish, WishResponse.class);
    }
}
