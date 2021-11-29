package com.rastatech.secretrasta.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String info;

    @NotEmpty
    private String location;

    @NotEmpty
    private String avatar;
}
