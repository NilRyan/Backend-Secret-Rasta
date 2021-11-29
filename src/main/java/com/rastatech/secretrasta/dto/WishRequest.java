package com.rastatech.secretrasta.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class WishRequest {

    @NotEmpty
    private String wishName;

    @NotEmpty
    private String imageUrl;

    @NotEmpty
    private String description;

    @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private int rastagemsRequired;
}
