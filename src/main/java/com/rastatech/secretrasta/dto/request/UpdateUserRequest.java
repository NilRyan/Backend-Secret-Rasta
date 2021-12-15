package com.rastatech.secretrasta.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateUserRequest {

    @Email(regexp = ".+@.+\\..+")
    private String email;
    private String firstName;
    private String lastName;

    @Pattern(regexp = "[0-9]{11}")
    private String phoneNumber;
    private String username;
    private String avatar;
}
