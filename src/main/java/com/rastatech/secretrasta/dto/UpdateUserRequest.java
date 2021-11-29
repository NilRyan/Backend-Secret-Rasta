package com.rastatech.secretrasta.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdateUserRequest {

    @Email(regexp = ".+@.+\\..+")
    private String email;
    private String username;
    private String info;
    private String location;
    private String avatar;
}
