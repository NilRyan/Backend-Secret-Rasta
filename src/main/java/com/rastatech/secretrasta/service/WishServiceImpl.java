package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishPageResponse;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.model.*;
import com.rastatech.secretrasta.repository.DonationRepository;
import com.rastatech.secretrasta.repository.LikeRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public WishEntity createWish(Long userId, WishRequest wish) {
        UserEntity user = fetchUser(userId);
        WishEntity wishEntity = modelMapper.map(wish, WishEntity.class);
        wishEntity.setUser(user);
        return wishRepository.save(wishEntity);
    }

    @Override
    public List<WishEntity> fetchWishes(Pageable pageable) {
        List<WishEntity> wishes = wishRepository.findAll(pageable);
        wishes.forEach(wish -> wish.setIsLiked(wish.getUser().getUserId()));
        return wishes;
    }

    @Override
    public List<WishEntity> fetchWishesByUser(Long userId, Pageable pageable) {
        UserEntity user = fetchUser(userId);
        return wishRepository.findAllByUser(user, pageable);
    }

    @Override
    public WishEntity fetchWish(Long wishId) {
        return wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void updateWish(Long wishId, UpdateWishRequest wish) {
        WishEntity wishEntity = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(wish, wishEntity);
        wishRepository.save(wishEntity);
    }

    @Override
    public void deleteWish(Long wishId) {
        wishRepository.deleteById(wishId);
    }

    @Override
    public WishEntity fetchWishWithMoreDetails(Long wishId, Long userId) {
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        wish.setIsLiked(userId);
        return wish;
    }

    @Override
    public List<WishEntity> fetchWishesGrantedByUser(Long userId, Pageable pageable) {
        List<WishEntity> grantedUserWishes = fetchWishesByUser(userId, pageable)
                .stream()
                .filter(wish -> wish.getRastagemsDonated() == wish.getRastagemsRequired())
                .collect(Collectors.toList());
        grantedUserWishes.forEach(wish -> wish.setIsLiked(userId));
        return grantedUserWishes;
    }

    @Override
    public List<WishEntity> fetchLikedWishes(Long userId, Pageable pageable) {
        List<WishEntity> likedByUser = wishRepository.findByLikes_User_UserId(userId, pageable);
        likedByUser.forEach(wish -> wish.setIsLiked(userId));
        return likedByUser;
    }

    @Override
    public List<WishEntity> fetchDonatedWishes(Long userId, Pageable pageable) {
        List<WishEntity> donatedByUser = wishRepository.findDistinctByDonations_User_UserId(userId, pageable);
        donatedByUser.forEach(wish -> wish.setIsLiked(userId));
        return donatedByUser;
    }

    private UserEntity fetchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
