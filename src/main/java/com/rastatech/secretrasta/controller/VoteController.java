package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.WishVoteRequest;
import com.rastatech.secretrasta.dto.WishVoteResponse;
import com.rastatech.secretrasta.model.WishVoteEntity;
import com.rastatech.secretrasta.service.WishVoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes/{wish_id}/vote")
@RequiredArgsConstructor
public class VoteController {

    private ModelMapper modelMapper;
    private WishVoteService wishVoteService;

    @PostMapping
    public void vote(@PathVariable("user_id") Long userId,
                     @PathVariable("wish_id") Long wishId,
                     @Valid @RequestBody WishVoteRequest vote) {
        wishVoteService.vote(userId, wishId, vote);
    }

    @DeleteMapping("/{vote_id}")
    public void deleteVote(@PathVariable("wish_id") Long wishId,
                           @PathVariable("vote_id") Long voteId) {
        wishVoteService.deleteVote(wishId, voteId);
    }

    @GetMapping
    public List<WishVoteResponse> getAllVotes(@PathVariable("wish_id") Long wishId) {
        List<WishVoteEntity> wishes = wishVoteService.getAllVotes(wishId);
        return wishes.stream().map(this::mapToWishVoteResponse).collect(Collectors.toList());
    }

    private WishVoteResponse mapToWishVoteResponse(WishVoteEntity wish) {
        return modelMapper.map(wish, WishVoteResponse.class);
    }
}
