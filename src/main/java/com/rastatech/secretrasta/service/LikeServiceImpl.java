package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.model.LikeEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.repository.LikeRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;
    private WishRepository wishRepository;
    private UserRepository userRepository;

    @Override
    public void like(Long wishId, Long userId) {
        LikeEntity likeEntity = new LikeEntity();
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        likeEntity.setWish(wish);
        likeEntity.setUser(user);
        likeRepository.save(likeEntity);
    }

    @Override
    public void unlike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
