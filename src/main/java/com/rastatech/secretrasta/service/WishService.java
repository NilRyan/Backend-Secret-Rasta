package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateCommentRequest;
import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.model.CommentEntity;
import com.rastatech.secretrasta.model.WishEntity;

import java.util.List;

public interface WishService {
    WishEntity createWish(Long userId, WishRequest wish);
    List<WishEntity> fetchWishes();
    List<WishEntity> fetchWishesByUser(Long userId);
    WishEntity fetchWish(Long wishId);
    void updateWish(Long wishId, UpdateWishRequest wish);
    void deleteWish(Long wishId);
}
