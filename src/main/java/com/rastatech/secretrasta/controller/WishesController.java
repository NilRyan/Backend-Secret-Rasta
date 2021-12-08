package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishPageResponse;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.dto.WishResponse;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.service.UserService;
import com.rastatech.secretrasta.service.WishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes")
@RequiredArgsConstructor
public class WishesController {

    private final ModelMapper modelMapper;
    private final WishService wishService;
    private final UserService userService;

    @PostMapping
    public WishResponse createWish(Authentication auth,
                                   @Valid @RequestBody WishRequest wishRequest) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        WishEntity wish = wishService.createWish(userId, wishRequest);
        return mapToWishResponse(wish);
    }

    @GetMapping
    public List<WishPageResponse> fetchWishes(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        return wishService.fetchWishes(pageable);
    }

    @GetMapping("/user/{user_id}")
    public List<WishResponse> fetchWishesByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> limit,
                                                @RequestParam Optional<String> sort,
                                                @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<WishEntity> wishes = wishService.fetchWishesByUser(userId, pageable);
        return wishes.stream().map(this::mapToWishResponse).collect(Collectors.toList());
    }

    @GetMapping("/{wish_id}")
    public WishPageResponse fetchWish(@PathVariable("wish_id") Long wishId,
                                      Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        return wishService.fetchWishWithMoreDetails(wishId, userId);
    }

    @GetMapping("/granted/{user_id}")
    public List<WishPageResponse> fetchWishesGrantedByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> limit,
                                                           @RequestParam Optional<String> sort,
                                                           @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        return wishService.fetchWishesGrantedByUser(userId, pageable);
    }


    @GetMapping("/liked/{user_id}")
    public List<WishPageResponse> fetchLikedWishes(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                   @RequestParam Optional<Integer> limit,
                                                   @RequestParam Optional<String> sort,
                                                   @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        return wishService.fetchLikedWishes(userId, pageable);
    }

    @GetMapping("/donated/{user_id}")

    public List<WishPageResponse> fetchWishesDonatedByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> limit,
                                                           @RequestParam Optional<String> sort,
                                                           @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        return wishService.fetchDonatedWishes(userId, pageable);
    }

    @PutMapping("/{wish_id}")
    public void updateWish(@PathVariable("wish_id") Long wishId,
                           @Valid @RequestBody UpdateWishRequest wish,
                           Authentication auth) {
        if (!auth.getPrincipal().equals(wishService.fetchWish(wishId).getUser().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        wishService.updateWish(wishId, wish);
    }

    @DeleteMapping("/{wish_id}")
    public void deleteWish(@PathVariable("wish_id") Long wishId,
                           Authentication auth) {
        if (!auth.getPrincipal().equals(wishService.fetchWish(wishId).getUser().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        wishService.deleteWish(wishId);
    }

    private Pageable getPageable(Optional<Integer> page, Optional<Integer> limit, Optional<String> sort, Optional<String> direction) {
        Sort.Direction sortDirection = direction.map(Sort.Direction::fromString).orElse(Sort.Direction.ASC);
        return PageRequest.of(page.orElseGet(() -> 0),
                limit.orElseGet(() -> 10),
                Sort.by(sort.orElseGet(() -> "updatedAt"))
                        .and(Sort.by(sortDirection)));
    }

    private WishResponse mapToWishResponse(WishEntity wish) {
        return modelMapper.map(wish, WishResponse.class);
    }

}

