package com.rastatech.secretrasta.service;

public interface LikeService {
    void like(Long wishId, Long userId);
    void unlike(Long likeId);
}
