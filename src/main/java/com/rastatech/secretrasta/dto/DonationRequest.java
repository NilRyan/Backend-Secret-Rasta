package com.rastatech.secretrasta.dto;

import javax.validation.constraints.Pattern;

public class DonationRequest {

    @Pattern(regexp = "[0-9]+]")
    private int amount;
}
