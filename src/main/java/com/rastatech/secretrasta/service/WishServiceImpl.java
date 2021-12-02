package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateWishRequest;
import com.rastatech.secretrasta.dto.WishPageResponse;
import com.rastatech.secretrasta.dto.WishRequest;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.model.WishVoteEntity;
import com.rastatech.secretrasta.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final WishVoteService wishVoteService;
    private final DonationService donationService;
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
        return StreamSupport.stream(wishRepository.findAll(pageable).spliterator(), false)
                .collect(Collectors.toList());
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

    @Override
    public WishPageResponse fetchWishWithMoreDetails(Long wishId, Long userId) {
        WishPageResponse wishPageResponse = new WishPageResponse();
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(wish, wishPageResponse);
        UserEntity user = fetchUser(userId);
        Long likeId = likeRepository.findByWishAndUser(wish, user).getLikeId();
        wishPageResponse.setLiked(likeId != null);
        wishPageResponse.setRastagemsCurrent(donationService.fetchDonationsByWish(wishId));
        int upvote = (int) wishVoteService.fetchVotes(wishId).stream().filter(a -> a.getVoteType().equals(1)).count();
        int downvote = (int) wishVoteService.fetchVotes(wishId).stream().filter(a -> a.getVoteType().equals(-1)).count();
        wishPageResponse.setDownvotes(downvote);
        wishPageResponse.setUpvotes(upvote);
        return wishPageResponse;
    }

    private UserEntity fetchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
