package com.rastatech.secretrasta.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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

    @NotNull
    @Min(1)
    private Integer rastagemsRequired;
}
