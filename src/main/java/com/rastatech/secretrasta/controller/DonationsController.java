package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationsController {
    private DonationService donationService;

    @GetMapping("/{wish_id}")
    public int fetchDonationsByWish(@PathVariable("wish_id") Long wishId) {
        return donationService.fetchDonationsByWish(wishId);
    }

    @GetMapping("/{user_id}")
    public int fetchDonationsByUser(@PathVariable("user_id") Long userId) {
        return donationService.fetchDonationsByUser(userId);
    }
}
