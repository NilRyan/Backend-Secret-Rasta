package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.dto.WishResponse;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.repository.WishRepository;
import com.rastatech.secretrasta.service.UserService;
import com.rastatech.secretrasta.service.WishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes")
@RequiredArgsConstructor
public class WishesController {

    private final ModelMapper modelMapper;
    private final WishService wishService;
    private final UserService userService;
    private final WishRepository wishRepository;

    @PostMapping
    public WishResponse createWish(Authentication auth,
                                   @Valid @RequestBody WishRequest wishRequest) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        WishEntity wish = wishService.createWish(userId, wishRequest);
        return mapToWishResponse(wish);
    }

    @GetMapping
    public List<WishResponse> fetchWishes() {
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
                           @Valid @RequestBody UpdateWishRequest wish,
                           Authentication auth) {
        if (!auth.getPrincipal().equals(wishRepository.findById(wishId).get().getUser().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        wishService.updateWish(wishId, wish);
    }

    @DeleteMapping("/{wish_id}")
    public void deleteWish(@PathVariable("wish_id") Long wishId,
                           Authentication auth) {
        if (!auth.getPrincipal().equals(wishRepository.findById(wishId).get().getUser().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        wishService.deleteWish(wishId);
    }

    private WishResponse mapToWishResponse(WishEntity wish) {
        return modelMapper.map(wish, WishResponse.class);
    }
}
