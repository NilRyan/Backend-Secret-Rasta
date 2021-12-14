package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.DonationRequest;
import com.rastatech.secretrasta.service.DonationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/donate")
@RequiredArgsConstructor
public class DonationsController {
    private final DonationService donationService;

    @PostMapping("/{wish_id}")
    @ApiOperation(value = "Donate rastagems to a wish",
            notes = "Provide the wish_id of the wish to be donated by the authenticated user. Donation to own wish is not allowed")
    public void createDonation(@PathVariable("wish_id") Long wishId, @Valid @RequestBody DonationRequest donation, Authentication auth ) {
        String username = (String) auth.getPrincipal();
        donationService.createDonation(wishId, username, donation);
    }


//    @GetMapping("/{wish_id}")
//    public int fetchDonationsByWish(@PathVariable("wish_id") Long wishId) {
//        return donationService.fetchDonationsByWish(wishId);
//    }
//
//    @GetMapping("/{user_id}")
//    public int fetchDonationsByUser(@PathVariable("user_id") Long userId) {
//        return donationService.fetchDonationsByUser(userId);
//    }
}
