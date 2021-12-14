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
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @ApiOperation(value = "Donate rastagems to a wish",
            notes = "Provide the wish_id of the wish to be donated by the authenticated user. Donation to own wish is not allowed")
    public void createDonation(@PathVariable("wish_id") Long wishId, @Valid @RequestBody DonationRequest donation, Authentication auth) {
        String username = (String) auth.getPrincipal();
        donationService.createDonation(wishId, username, donation);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add Rastagems to own account",
            notes = "Provide the request body as specified below to add rastagem balance to own account")
    public void addBalance(@Valid @RequestBody AddBalanceRequest addBalanceRequest, Authentication auth) {
        String username = (String) auth.getPrincipal();
        addBalanceService.addBalance(username, addBalanceRequest);
    }

    @PostMapping("/transfer/{username}")
    @ApiOperation(value = "Transfer rastagems to another user",
            notes = "Use this to transfer rastagems to another user")
    public void sendRastaGemsToUser(@PathVariable("username") String toUsername,
                                    @Valid @RequestBody SendGemsRequest sendGemsRequest,
                                    Authentication auth) {
        String username = (String) auth.getPrincipal();
        sendGemsService.sendGemsToUser(toUsername, username, sendGemsRequest);
    }

    @GetMapping("/history")
    @ApiOperation(value = "View all transaction history",
            notes = "Use this api to view history for all transactions e.g. donations, add gems, and send gems transactions")
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
                    .transactionTime(dateTime.toLocalTime().toString())
                    .transactionDetails("Donation to Wish " + transaction.getWish().getWishId())
                    .amount("-" + transaction.getAmount())
                    .build();
            transactionHistory.add(donation);
        });
        receivedDonations.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse donation = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime().toString())
                    .transactionDetails("Received donation " + transaction.getDonationId())
                    .amount("+" + transaction.getAmount())
                    .build();
            transactionHistory.add(donation);
        });
        sentGems.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse sendGemTransac = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime().toString())
                    .transactionDetails("Sent Rasta Gems to User " + transaction.getSendGemTo().getUserId())
                    .amount("-" + transaction.getAmount())
                    .build();
            transactionHistory.add(sendGemTransac);

        });
        receivedGems.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse receivedGemsTransac = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime().toString())
                    .transactionDetails("Received Rasta Gems from User" + transaction.getSendGemFrom().getUserId())
                    .amount("+" + transaction.getAmount())
                    .build();
            transactionHistory.add(receivedGemsTransac);

        });
        addBalance.forEach((transaction) -> {
            LocalDateTime dateTime = transaction.getTransactionDate();
            TransactionHistoryResponse addBalanceTransac = TransactionHistoryResponse.builder()
                    .transactionDate(dateTime.toLocalDate())
                    .transactionTime(dateTime.toLocalTime().toString())
                    .transactionDetails("Add Balance")
                    .amount("+" + transaction.getAmount())
                    .build();
            transactionHistory.add(addBalanceTransac);
        });

        Comparator<TransactionHistoryResponse> compareByDate = (TransactionHistoryResponse t1, TransactionHistoryResponse t2) -> {
            LocalDateTime dateT1 = LocalDateTime.of(t1.getTransactionDate(), LocalTime.parse(t1.getTransactionTime()));
            LocalDateTime dateT2 = LocalDateTime.of(t2.getTransactionDate(), LocalTime.parse(t2.getTransactionTime()));
            return dateT2.compareTo(dateT1);
        };

        transactionHistory.sort(compareByDate);
        return transactionHistory;
    }


}
