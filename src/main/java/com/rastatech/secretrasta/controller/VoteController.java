package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.WishVoteRequest;
import com.rastatech.secretrasta.dto.WishVoteResponse;
import com.rastatech.secretrasta.model.WishVoteEntity;
import com.rastatech.secretrasta.repository.WishVoteRepository;
import com.rastatech.secretrasta.service.UserService;
import com.rastatech.secretrasta.service.WishVoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishes/{wish_id}/vote")
@RequiredArgsConstructor
public class VoteController {

    private final ModelMapper modelMapper;
    private final WishVoteService wishVoteService;
    private final UserService userService;

    @PostMapping
    public void vote(Authentication auth,
                     @PathVariable("wish_id") Long wishId,
                     @Valid @RequestBody WishVoteRequest vote) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        wishVoteService.vote(userId, wishId, vote);
    }

//    @DeleteMapping("/{vote_id}")
//    public void deleteVote(@PathVariable("wish_id") Long wishId,
//                           @PathVariable("vote_id") Long voteId,
//                           Authentication auth) {
//        if (!auth.getPrincipal().equals(wishVoteService.fetchVote(voteId).getUser().getUsername()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        wishVoteService.deleteVote(wishId, voteId);
//    }

    @GetMapping
    public List<WishVoteResponse> getAllVotes(@PathVariable("wish_id") Long wishId) {
        List<WishVoteEntity> wishes = wishVoteService.fetchVotes(wishId);
        return wishes.stream().map(this::mapToWishVoteResponse).collect(Collectors.toList());
    }

    private WishVoteResponse mapToWishVoteResponse(WishVoteEntity wish) {
        return modelMapper.map(wish, WishVoteResponse.class);
    }
}
