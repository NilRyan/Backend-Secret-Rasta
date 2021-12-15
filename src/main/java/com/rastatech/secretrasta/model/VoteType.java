package com.rastatech.secretrasta.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(0), DOWNVOTE(1), NONE(2)
    ;

    private int direction;

    VoteType(int direction) {}

    public static VoteType lookup(int direction) {
        return Arrays.stream(VoteType.values())
                .filter(val -> val.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Integer getDirection() {
        return direction;
    }
}
