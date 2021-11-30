package com.rastatech.secretrasta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequest {

    @Pattern(regexp = "[0-9]+")
    private int amount;
}
