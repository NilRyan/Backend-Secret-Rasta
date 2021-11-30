package com.rastatech.secretrasta.dto;

import javax.validation.constraints.Email;

public class UpdateUserRequest {

    @Email(regexp = ".+@.+\\..+")
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String info;
    private String avatar;
}
