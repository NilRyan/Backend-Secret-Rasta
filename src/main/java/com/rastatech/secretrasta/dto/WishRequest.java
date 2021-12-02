package com.rastatech.secretrasta.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WishRequest {

    @NotEmpty
    private String wishName;

    @NotEmpty
    private String imageUrl;

    @NotEmpty
    private String description;

    @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private Integer rastagemsRequired;
}
