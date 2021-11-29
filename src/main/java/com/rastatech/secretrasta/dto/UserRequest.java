package com.rastatech.secretrasta.dto;

import javax.validation.constraints.*;

public class UserRequest {

    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;

    @NotEmpty
    private String username;

    @NotEmpty
    private String info;

    @NotEmpty
    private String avatar;
}
