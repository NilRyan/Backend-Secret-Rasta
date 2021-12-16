package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.UpdateWishRequest;
import com.rastatech.secretrasta.dto.request.WishRequest;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WishService {
    WishEntity createWish(Long userId, WishRequest wish);
    List<WishEntity> fetchWishes(Optional<String> search, Long userId, Pageable pageable);
    List<WishEntity> fetchWishesByUser(Long userId, Pageable pageable);
    WishEntity fetchWish(Long wishId);
    void updateWish(Long wishId, UpdateWishRequest wish);
    void deleteWish(Long wishId);
    WishEntity fetchWishWithMoreDetails(Long wishId, Long userId);
    List<WishEntity> fetchWishesFulfilledByUser(Long userId, Pageable pageable);
    List<WishEntity> fetchLikedWishes(Long userId, Pageable pageable);
    List<WishEntity> fetchDonatedWishes(Long userId, Pageable pageable);
    int activeWishCount(Long userId);
    int fulfilledWishCount(Long userId);
    List<WishEntity> fetchActiveWishesByUser(Long userId, Pageable pageable);
}
