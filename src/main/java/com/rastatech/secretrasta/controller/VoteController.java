package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.WishVoteRequest;
import com.rastatech.secretrasta.service.UserService;
import com.rastatech.secretrasta.service.WishVoteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/wishes/{wish_id}/vote")
@RequiredArgsConstructor
public class VoteController {

    private final ModelMapper modelMapper;
    private final WishVoteService wishVoteService;
    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "Upvote, Downvote, or Unvote a wish",
            notes = "When the wish is currently not voted by the user, using this api with any of the vote types UPVOTE or DOWNVOTE will save the vote on the wish." +
                    "If the wish is currently voted, using this api with the same vote type as the current vote will cancel the vote and unvote the wish." +
                    "If the wish is currently voted, using this api the opposite vote type as the current vote will replace the current vote with the opposite vote type.")
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

//    @GetMapping
//    public List<WishVoteResponse> getAllVotes(@PathVariable("wish_id") Long wishId) {
//        List<WishVoteEntity> wishes = wishVoteService.fetchVotes(wishId);
//        return wishes.stream().map(this::mapToWishVoteResponse).collect(Collectors.toList());
//    }
//
//    private WishVoteResponse mapToWishVoteResponse(WishVoteEntity wish) {
//        return modelMapper.map(wish, WishVoteResponse.class);
//    }
}
