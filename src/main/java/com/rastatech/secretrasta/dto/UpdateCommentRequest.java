package com.rastatech.secretrasta.dto;

import javax.validation.constraints.NotEmpty;

public class UpdateCommentRequest {

    @NotEmpty
    private String comment;

}
