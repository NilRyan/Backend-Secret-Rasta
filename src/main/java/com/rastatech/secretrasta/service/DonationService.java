package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.DonationRequest;

public interface DonationService {
    void donate(Long wishId, Long donationId, DonationRequest amount);
    int fetchWishDonations(Long wishId);
    int fetchUserDonations(Long userId);
}
