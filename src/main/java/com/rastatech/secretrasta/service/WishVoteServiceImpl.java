package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.WishVoteRequest;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.model.WishEntity;
import com.rastatech.secretrasta.model.WishVoteEntity;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import com.rastatech.secretrasta.repository.WishVoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishVoteServiceImpl implements WishVoteService {

    private WishRepository wishRepository;
    private UserRepository userRepository;
    private WishVoteRepository wishVoteRepository;
    private ModelMapper modelMapper;

    @Override
    public void vote(Long userId, Long wishId, WishVoteRequest vote) {
        WishVoteEntity wishVote = new WishVoteEntity();
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        wishVote.setVoteType(vote.getVoteType());
        wishVote.setWish(wish);
        wishVote.setUser(user);
        wishVoteRepository.save(wishVote);
    }

    @Override
    public void deleteVote(Long wishId, Long voteId) {
        wishVoteRepository.deleteById(voteId);
    }

    @Override
    public List<WishVoteEntity> getAllVotes(Long wishId) {
        WishEntity wish = wishRepository.findById(wishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return wishVoteRepository.findAllByWish(wish);
    }
}
