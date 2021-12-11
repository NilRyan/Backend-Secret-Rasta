package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.AddBalanceRequest;
import com.rastatech.secretrasta.dto.request.DonationRequest;
import com.rastatech.secretrasta.dto.request.SendGemsRequest;
import com.rastatech.secretrasta.dto.response.TransactionHistoryResponse;
import com.rastatech.secretrasta.model.AddBalanceEntity;
import com.rastatech.secretrasta.model.DonationEntity;
import com.rastatech.secretrasta.model.SendGemsEntity;
import com.rastatech.secretrasta.service.AddBalanceService;
import com.rastatech.secretrasta.service.DonationService;
import com.rastatech.secretrasta.service.SendGemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rastagem")
@RequiredArgsConstructor
public class GemsController {

    private final AddBalanceService addBalanceService;
    private final SendGemsService sendGemsService;
    private final DonationService donationService;


    @PostMapping("/donate/{wish_id}")
    public void createDonation(@PathVariable("wish_id") Long wishId, @Valid @RequestBody DonationRequest donation, Authentication auth ) {
        String username = (String) auth.getPrincipal();
        donationService.createDonation(wishId, username, donation);
    }

    @PostMapping("/add")
    public void addBalance(@Valid @RequestBody AddBalanceRequest addBalanceRequest, Authentication auth) {
        String username = (String) auth.getPrincipal();
        addBalanceService.addBalance(username, addBalanceRequest);
    }
    @PostMapping("/transfer/{user_id}")
    public void sendRastaGemsToUser(@PathVariable("user_id") Long sendToUserId,
                                    @Valid @RequestBody SendGemsRequest sendGemsRequest,
                                    Authentication auth ) {
        String username = (String) auth.getPrincipal();
        sendGemsService.sendGemsToUser(sendToUserId, username, sendGemsRequest);
    }

    @GetMapping("/history")
    public List<TransactionHistoryResponse> getTransactionHistory(Authentication auth) {
        String username = (String) auth.getPrincipal();
        List<DonationEntity>  donations = donationService.fetchDonationsByUser(username);
        List<SendGemsEntity> sentGems = sendGemsService.fetchSendGemsTransactions(username);
        List<AddBalanceEntity> addBalance = addBalanceService.fetchAddBalanceTransactions(username);


    }


}
