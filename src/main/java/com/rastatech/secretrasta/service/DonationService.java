package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.DonationRequest;
import com.rastatech.secretrasta.model.DonationEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonationService {
    void createDonation(Long wishId, String username, DonationRequest donation);
    List<DonationEntity> fetchDonationsByUser(String username);
    List<DonationEntity> fetchReceivedDonationsByUser(String username);
    List<DonationEntity> fetchDonationsByWishId(Long wishId, Pageable pageable);
}
