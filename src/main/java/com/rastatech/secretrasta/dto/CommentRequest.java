package com.rastatech.secretrasta.dto;

import javax.validation.constraints.NotEmpty;

public class CommentRequest {

    @NotEmpty
    private String comment;
}
