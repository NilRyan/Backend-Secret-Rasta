package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.repository.DonationRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.repository.WishRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceImplTest {
    @Mock
    private DonationRepository donationRepository;
    @Mock
    private WishRepository wishRepository;
    @Mock
    private UserRepository userRepository;

    @Autowired
    private DonationServiceImpl underTest;

    @Test
    void shouldTransferRastaGemsToWish() {

    }

    @Test
    @Disabled
    void createDonation() {
    }
}