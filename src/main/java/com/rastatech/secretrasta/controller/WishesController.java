package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.UpdateWishRequest;
import com.rastatech.secretrasta.dto.response.WishDonatorsResponse;
import com.rastatech.secretrasta.dto.response.WishPageResponse;
import com.rastatech.secretrasta.dto.request.WishRequest;
import com.rastatech.secretrasta.dto.response.WishResponse;
import com.rastatech.secretrasta.dto.response.WishesStatusResponse;
import com.rastatech.secretrasta.model.DonationEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.service.DonationService;
import com.rastatech.secretrasta.service.UserService;
import com.rastatech.secretrasta.service.WishService;
import io.swagger.annotations.ApiOperation;
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
    private final DonationService donationService;

    @PostMapping
    @ApiOperation(value = "Create a wish and store in database",
            notes = "Provide the request body as specified below.")
    public WishResponse createWish(Authentication auth,
                                   @Valid @RequestBody WishRequest wishRequest) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        WishEntity wish = wishService.createWish(userId, wishRequest);
        return mapToWishResponse(wish);
    }

    @GetMapping
    @ApiOperation(value = "Fetch all wishes in the database",
            notes = "Use this api to fetch all wishes in the database. You may provide details for pagination e.g. " +
                    "page number, limit per page, sort by field, and sort direction.")
    public List<WishPageResponse> fetchWishes(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> direction, Authentication auth) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        List<WishEntity> wishEntities = wishService.fetchWishes(userId, pageable);
        return wishEntities.stream().map(this::mapToWishPageResponse).collect(Collectors.toList());

    }

    @GetMapping("/donators/{wish_id}")
    @ApiOperation(value = "Fetch all donators of the wish",
            notes = "Use this api to fetch all donators of a wish")
    public List<WishDonatorsResponse> fetchDonatorsOfWish(@PathVariable("wish_id") Long wishId, @RequestParam Optional<Integer> page,
                                                          @RequestParam Optional<Integer> limit,
                                                          @RequestParam Optional<String> sort,
                                                          @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<DonationEntity> donators = donationService.fetchDonationsByWishId(wishId, pageable);
        return donators.stream().map(this::mapToWishDonatorsResponse).collect(Collectors.toList());
    }

    @GetMapping("/user/{user_id}")
    @ApiOperation(value = "Fetch all wishes of the specified user",
            notes = "Use this api to fetch all wishes of the specified user. You may provide details for pagination e.g. " +
                    "page number, limit per page, sort by field, and sort direction.")
    public List<WishPageResponse> fetchWishesByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> limit,
                                                @RequestParam Optional<String> sort,
                                                @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<WishEntity> wishes = wishService.fetchWishesByUser(userId, pageable);
        return wishes.stream().map(this::mapToWishPageResponse).collect(Collectors.toList());
    }

    @GetMapping("/{wish_id}")
    @ApiOperation(value = "Fetch a specific wish",
            notes = "Response body is shown below.")
    public WishPageResponse fetchWish(@PathVariable("wish_id") Long wishId,
                                      Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        WishEntity wish = wishService.fetchWishWithMoreDetails(wishId, userId);
        return mapToWishPageResponse(wish);
    }

    @GetMapping("/fulfilled/{user_id}")
    @ApiOperation(value = "Fetch all fulfilled wishes of the specified user",
            notes = "Use this api to fetch all fulfilled wishes of the specified user. You may provide details for pagination e.g. " +
                    "page number, limit per page, sort by field, and sort direction.")
    public List<WishPageResponse> fetchWishesGrantedByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> limit,
                                                           @RequestParam Optional<String> sort,
                                                           @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<WishEntity> wishEntities = wishService.fetchWishesFulfilledByUser(userId, pageable);
        return wishEntities.stream().map(this::mapToWishPageResponse).collect(Collectors.toList());
    }

    @GetMapping("/active/{user_id}")
    @ApiOperation(value = "Fetch all active wishes of the specified user",
            notes = "Use this api to fetch all active wishes of the specified user (wishes not yet fulfilled). You may provide details for pagination e.g. " +
                    "page number, limit per page, sort by field, and sort direction.")
    public List<WishPageResponse> fetchActiveWishesByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                          @RequestParam Optional<Integer> limit,
                                                          @RequestParam Optional<String> sort,
                                                          @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<WishEntity> wishEntities = wishService.fetchActiveWishesByUser(userId, pageable);
        return wishEntities.stream().map(this::mapToWishPageResponse).collect(Collectors.toList());
    }

    @GetMapping("/status/{user_id}")
    @ApiOperation(value = "Fetch the number of active wishes and fulfilled wishes",
            notes = "Use this api to fetch the number of active wishes and fulfilled wishes")
    public WishesStatusResponse fetchWishesStatusByUser(@PathVariable("user_id") Long userId) {
        int activeWishes = wishService.activeWishCount(userId);
        int fulfilledWishes = wishService.fulfilledWishCount(userId);
        return WishesStatusResponse.builder()
                .activeWishes(activeWishes)
                .fulfilledWishes(fulfilledWishes)
                .build();
    }



    @GetMapping("/liked/{user_id}")
    @ApiOperation(value = "Fetch all wishes liked by the specified user",
            notes = "Use this api to fetch all wishes liked by the specified user. You may provide details for pagination e.g. " +
                    "page number, limit per page, sort by field, and sort direction.")
    public List<WishPageResponse> fetchLikedWishes(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                   @RequestParam Optional<Integer> limit,
                                                   @RequestParam Optional<String> sort,
                                                   @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<WishEntity> wishEntities = wishService.fetchLikedWishes(userId, pageable);
        return wishEntities.stream().map(this::mapToWishPageResponse).collect(Collectors.toList());
    }

    @GetMapping("/donated/{user_id}")
    @ApiOperation(value = "Fetch all wishes in which the specified user has donated",
            notes = "Use this api to fetch all wishes donated by the specified user. You may provide details for pagination e.g. " +
                    "page number, limit per page, sort by field, and sort direction.")
    public List<WishPageResponse> fetchWishesDonatedByUser(@PathVariable("user_id") Long userId, @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> limit,
                                                           @RequestParam Optional<String> sort,
                                                           @RequestParam Optional<String> direction) {
        Pageable pageable = getPageable(page, limit, sort, direction);
        List<WishEntity> wishEntities = wishService.fetchDonatedWishes(userId, pageable);
        return wishEntities.stream().map(this::mapToWishPageResponse).collect(Collectors.toList());
    }

    @PutMapping("/{wish_id}")
    @ApiOperation(value = "Edit any or all of the details of a wish",
            notes = "Only the owner of the wish may edit his own wish.")
    public void updateWish(@PathVariable("wish_id") Long wishId,
                           @Valid @RequestBody UpdateWishRequest wish,
                           Authentication auth) {
        if (!auth.getPrincipal().equals(wishService.fetchWish(wishId).getUser().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        wishService.updateWish(wishId, wish);
    }

    @DeleteMapping("/{wish_id}")
    @ApiOperation(value = "Delete a wish",
            notes = "Only the owner of the wish may edit his own wish.")
    public void deleteWish(@PathVariable("wish_id") Long wishId,
                           Authentication auth) {
        if (!auth.getPrincipal().equals(wishService.fetchWish(wishId).getUser().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        wishService.deleteWish(wishId);
    }



    private Pageable getPageable(Optional<Integer> page, Optional<Integer> limit, Optional<String> sort, Optional<String> direction) {
        Sort.Direction sortDirection = direction.map(Sort.Direction::fromString).orElse(Sort.Direction.ASC);
        return PageRequest.of(page.orElse(0),
                limit.orElse(10),
                sortDirection,
                sort.orElse("updatedAt"));
    }

    private WishResponse mapToWishResponse(WishEntity wish) {
        return modelMapper.map(wish, WishResponse.class);
    }

    private WishPageResponse mapToWishPageResponse(WishEntity wish) {
        return modelMapper.map(wish, WishPageResponse.class);
    }

    private WishDonatorsResponse mapToWishDonatorsResponse(DonationEntity donators) {
        return modelMapper.map(donators, WishDonatorsResponse.class);
    }
}

