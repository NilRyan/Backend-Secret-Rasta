package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.DonationRequest;

public interface DonationService {
    void createDonation(Long wishId, String username, DonationRequest donation);
    int fetchDonationsByWish(Long wishId);
    int fetchDonationsByUser(Long userId);
}
