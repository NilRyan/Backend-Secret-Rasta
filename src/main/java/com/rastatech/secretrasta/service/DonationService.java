package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.DonationRequest;

public interface DonationService {
    void donate(Long wishId, Long userId, DonationRequest donation);
    int fetchDonationsByWish(Long wishId);
    int fetchDonationsByUser(Long userId);
}
