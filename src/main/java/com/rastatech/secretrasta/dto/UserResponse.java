package com.rastatech.secretrasta.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String info;
    private String location;
    private String avatar;
}
