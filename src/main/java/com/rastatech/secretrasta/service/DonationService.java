package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.DonationRequest;
import com.rastatech.secretrasta.model.DonationEntity;

import java.util.List;

public interface DonationService {
    void createDonation(Long wishId, String username, DonationRequest donation);
    List<DonationEntity> fetchDonationsByUser(String username);
    List<DonationEntity> fetchReceivedDonationsByUser(String username);
}
