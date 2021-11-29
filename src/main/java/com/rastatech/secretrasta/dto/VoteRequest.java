package com.rastatech.secretrasta.dto;

import com.rastatech.secretrasta.model.VoteType;

import javax.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull
    private VoteType voteType;

    @NotNull
    private Long id;
}
