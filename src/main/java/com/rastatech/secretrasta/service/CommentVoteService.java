package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.VoteRequest;

public interface CommentVoteService {
    void vote(VoteRequest vote);
}
