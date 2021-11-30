package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.LikeRequest;

public interface LikeService {
    void like(LikeRequest like);
    void unlike(LikeRequest like);
}
