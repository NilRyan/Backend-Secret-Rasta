package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.model.DonationEntity;

import java.util.List;

public interface DonationService {
    void donate(Long donationId);
    int fetchWishDonations(Long wishId);
    int fetchUserDonations(Long userId);
}
