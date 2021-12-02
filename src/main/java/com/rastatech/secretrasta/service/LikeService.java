package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.model.LikeEntity;

public interface LikeService {
    void like(Long wishId, Long userId);
    void unlike(Long likeId);
    LikeEntity fetchLike(Long likeId);
}
