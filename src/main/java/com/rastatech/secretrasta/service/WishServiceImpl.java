package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.model.WishEntity;

import java.util.List;

public class WishServiceImpl implements WishService {
    @Override
    public WishEntity createWish(Long wishId, WishRequest wish) {
        return null;
    }

    @Override
    public List<WishEntity> fetchWishes() {
        return null;
    }

    @Override
    public WishEntity fetchWish(Long wishId) {
        return null;
    }

    @Override
    public void updateWish(Long wishId, UpdateWishRequest wish) {

    }

    @Override
    public void deleteWish(Long wishId) {

    }
}
