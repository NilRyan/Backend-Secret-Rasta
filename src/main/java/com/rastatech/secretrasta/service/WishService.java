package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishPageResponse;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.model.WishEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WishService {
    WishEntity createWish(Long userId, WishRequest wish);
    List<WishPageResponse> fetchWishes(Pageable pageable);
    List<WishEntity> fetchWishesByUser(Long userId);
    WishEntity fetchWish(Long wishId);
    void updateWish(Long wishId, UpdateWishRequest wish);
    void deleteWish(Long wishId);
    WishPageResponse fetchWishWithMoreDetails(Long wishId, Long userId);
    List<WishPageResponse> fetchWishesGrantedByUser(Long userId, Pageable pageable);
    List<WishPageResponse> fetchLikedWishes(Long userId, Pageable pageable);
    List<WishPageResponse> fetchDonatedWishes(Long userId);
}
