package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.UpdateWishRequest;
import com.rastatech.secretrasta.dto.request.WishRequest;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        List<WishEntity> wishes = wishRepository.findByDeletedFalse(pageable);
        wishes.forEach(wish -> wish.setIsLiked(wish.getUser().getUserId()));
        return wishes;
    }

    @Override
    public List<WishEntity> fetchWishesByUser(Long userId, Pageable pageable) {
        UserEntity user = fetchUser(userId);
        List<WishEntity> wishes = wishRepository.findByUserAndDeletedFalse(user, pageable);
        wishes.forEach(wish -> wish.setIsLiked(wish.getUser().getUserId()));
        return wishes;
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
        List<WishEntity> likedByUser = wishRepository.findByLikes_User_UserIdAndDeletedFalse(userId, pageable);
        likedByUser.forEach(wish -> wish.setIsLiked(userId));
        return likedByUser;
    }

    @Override
    public List<WishEntity> fetchDonatedWishes(Long userId, Pageable pageable) {
        List<WishEntity> donatedByUser = wishRepository.findDistinctByDonations_User_UserIdAndDeletedFalse(userId, pageable);
        donatedByUser.forEach(wish -> wish.setIsLiked(userId));
        return donatedByUser;
    }

    @Override
    public List<WishEntity> fetchActiveWishesByUser(Long userId, Pageable pageable) {
        List<WishEntity> activeWishes = fetchWishesByUser(userId, pageable)
                .stream()
                .filter(wish -> wish.getRastagemsDonated() != wish.getRastagemsRequired())
                .collect(Collectors.toList());
        activeWishes.forEach(wish -> wish.setIsLiked(userId));
        return activeWishes;
    }

    private UserEntity fetchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
