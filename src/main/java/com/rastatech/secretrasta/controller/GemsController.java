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
import com.rastatech.secretrasta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/rastagem")
@RequiredArgsConstructor
public class GemsController {

    private final AddBalanceService addBalanceService;
    private final SendGemsService sendGemsService;
    private final DonationService donationService;
    private final UserService userService;


    @PostMapping("/donate/{wish_id}")
    public void createDonation(@PathVariable("wish_id") Long wishId, @Valid @RequestBody DonationRequest donation, Authentication auth) {
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
                                    Authentication auth) {
        String username = (String) auth.getPrincipal();
        sendGemsService.sendGemsToUser(sendToUserId, username, sendGemsRequest);
    }

    @GetMapping("/history")
    public List<TransactionHistoryResponse> getTransactionHistory(Authentication auth) {
        String username = (String) auth.getPrincipal();
        List<DonationEntity> donations = donationService.fetchDonationsByUser(username);
        List<DonationEntity> receivedDonations = donationService.fetchReceivedDonationsByUser(username);
        List<SendGemsEntity> sentGems = sendGemsService.fetchSendGemsTransactions(username);
        List<SendGemsEntity> receivedGems = sendGemsService.fetchReceiveGemsTransactions(username);
        List<AddBalanceEntity> addBalance = addBalanceService.fetchAddBalanceTransactions(username);
        List<TransactionHistoryResponse> transactionHistory = new ArrayList<>();
        donations.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse donation = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime())
                    .transactionDetails("Donation to Wish " + transaction.getWish().getWishId())
                    .amount("-" + transaction.getAmount())
                    .build();
            transactionHistory.add(donation);
        });
        receivedDonations.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse donation = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime())
                    .transactionDetails("Received donation " + transaction.getDonationId())
                    .amount("+" + transaction.getAmount())
                    .build();
            transactionHistory.add(donation);
        });
        sentGems.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse sendGemTransac = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime())
                    .transactionDetails("Sent Rasta Gems to User " + transaction.getSendGemTo().getUserId())
                    .amount("-" + transaction.getAmount())
                    .build();
            transactionHistory.add(sendGemTransac);

        });
        receivedGems.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse receivedGemsTransac = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime())
                    .transactionDetails("Received Rasta Gems from User" + transaction.getSendGemFrom().getUserId())
                    .amount("+" + transaction.getAmount())
                    .build();
            transactionHistory.add(receivedGemsTransac);

        });
        addBalance.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse addBalanceTransac = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime())
                    .transactionDetails("Add Balance")
                    .amount("+" + transaction.getAmount())
                    .build();
            transactionHistory.add(addBalanceTransac);
        });

        Comparator<TransactionHistoryResponse> compareByDate = (TransactionHistoryResponse t1, TransactionHistoryResponse t2) -> {
            LocalDateTime dateT1 = LocalDateTime.of(t1.getTransactionDate(), t1.getTransactionTime());
            LocalDateTime dateT2 = LocalDateTime.of(t2.getTransactionDate(), t2.getTransactionTime());
            return dateT2.compareTo(dateT1);
        };

        transactionHistory.sort(compareByDate);
        return transactionHistory;
    }


}
