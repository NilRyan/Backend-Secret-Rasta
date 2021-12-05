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
    private final DonationRepository donationRepository;
    private final LikeRepository likeRepository;
    private final WishVoteService wishVoteService;
    private final ModelMapper modelMapper;

    @Override
    public WishEntity createWish(Long userId, WishRequest wish) {
        UserEntity user = fetchUser(userId);
        WishEntity wishEntity = modelMapper.map(wish, WishEntity.class);
        wishEntity.setUser(user);
        return wishRepository.save(wishEntity);
    }

    @Override
    public List<WishPageResponse> fetchWishes(Pageable pageable) {
        List<WishEntity> wishes = wishRepository.findAll(pageable);
        List<WishPageResponse> wishPageResponses = new ArrayList<>();
        wishes.forEach(wish -> wishPageResponses.add(fetchWishWithMoreDetails(wish.getWishId(), wish.getUser().getUserId())));
        return wishPageResponses;
    }

    @Override
    public List<WishEntity> fetchWishesByUser(Long userId) {
        UserEntity user = fetchUser(userId);
        return wishRepository.findAllByUser(user);
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
    /*
        TODO
            1. Decide where to map DTO, either on controller or service layer
     */
    @Override
    public WishPageResponse fetchWishWithMoreDetails(Long wishId, Long userId) {
        WishPageResponse wishPageResponse = new WishPageResponse();
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(wish, wishPageResponse);
        UserEntity user = fetchUser(userId);
        /* TODO - refactor to use jpa query like "existsBy" instead of fetching the whole vote entity
            -getLikeId() causes null pointer exception
         */
        Long likeId = likeRepository.findByWishAndUser(wish, user).getLikeId();
        wishPageResponse.setLiked(likeId != null);
        int upvote = (int) wishVoteService.fetchVotes(wishId).stream().filter(a -> a.getVoteType().equals(VoteType.UPVOTE)).count();
        int downvote = (int) wishVoteService.fetchVotes(wishId).stream().filter(a -> a.getVoteType().equals(VoteType.DOWNVOTE)).count();
        wishPageResponse.setDownvotes(downvote);
        wishPageResponse.setUpvotes(upvote);
        return wishPageResponse;
    }

    @Override
    public List<WishPageResponse> fetchWishesGrantedByUser(Long userId, Pageable pageable) {
        List<WishEntity> userWishes = fetchWishesByUser(userId)
                .stream()
                .filter(wish -> wish.getRastagemsDonated() > wish.getRastagemsRequired())
                .collect(Collectors.toList());
        List<WishPageResponse> wishPageResponses = new ArrayList<>();
        userWishes.forEach(wish -> wishPageResponses.add(fetchWishWithMoreDetails(wish.getWishId(), userId)));
        return wishPageResponses;
    }

    @Override
    public List<WishPageResponse> fetchLikedWishes(Long userId, Pageable pageable) {
        List<LikeEntity> likedByUser = likeRepository.findByUser(fetchUser(userId), pageable);
        List<WishPageResponse> wishPageResponses = new ArrayList<>();
        likedByUser.forEach(wish -> wishPageResponses.add(fetchWishWithMoreDetails(wish.getWish().getWishId(), userId)));
        return wishPageResponses;
    }

    @Override
    public List<WishPageResponse> fetchDonatedWishes(Long userId) {
        List<DonationEntity> donatedByUser = donationRepository.findByUser(fetchUser(userId));
        List<WishPageResponse> wishPageResponses = new ArrayList<>();
        donatedByUser.forEach(wish -> wishPageResponses.add(fetchWishWithMoreDetails(wish.getWish().getWishId(), userId)));
        return wishPageResponses;
    }

    private UserEntity fetchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
