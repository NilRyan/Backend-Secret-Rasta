package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.WishVoteRequest;
import com.rastatech.secretrasta.model.WishVoteEntity;

import java.util.List;

public interface WishVoteService {
    void vote(Long userId, Long wishId, WishVoteRequest vote);
    void deleteVote(Long wishId, Long voteId);
    List<WishVoteEntity> fetchVotes(Long wishId);
    WishVoteEntity fetchVote(Long voteId);
}
