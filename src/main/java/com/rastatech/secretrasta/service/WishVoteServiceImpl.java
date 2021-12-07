package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.WishVoteRequest;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.VoteType;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.model.WishVoteEntity;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import com.rastatech.secretrasta.repository.WishVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishVoteServiceImpl implements WishVoteService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final WishVoteRepository wishVoteRepository;

    @Override
    @Transactional
    public void vote(Long userId, Long wishId, WishVoteRequest vote) {
        WishVoteEntity wishVote = new WishVoteEntity();
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        VoteType voteType = vote.getVoteType();

        Optional<WishVoteEntity> existingVote = wishVoteRepository.findByWishAndUser(wish, user);

        if (existingVote.isEmpty()) {
            wishVote.setVoteType(voteType);
            wishVote.setWish(wish);
            wishVote.setUser(user);
            wishVoteRepository.save(wishVote);
        }
        else {
            WishVoteEntity voteExtracted = existingVote.get();
            if (voteType != voteExtracted.getVoteType()) {
                fetchVote(voteExtracted.getVoteId()).setVoteType(voteType);
            } else {
                deleteVote(wishId, voteExtracted.getVoteId());
            }
        }
    }

    @Override
    public void deleteVote(Long wishId, Long voteId) {
        wishVoteRepository.deleteById(voteId);
    }

    @Override
    public List<WishVoteEntity> fetchVotes(Long wishId) {
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return wishVoteRepository.findAllByWish(wish);
    }

    @Override
    public WishVoteEntity fetchVote(Long voteId) {
        return wishVoteRepository.findById(voteId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
