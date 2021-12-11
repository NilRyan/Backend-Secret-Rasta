package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.SendGemsRequest;
import com.rastatech.secretrasta.service.SendGemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sendgems")
@RequiredArgsConstructor
public class SendGemsController {

    private final SendGemsService sendGemsService;

    @PostMapping("/{user_id}")
    public void sendRastaGemsToUser(@PathVariable("user_id") Long sendToUserId,
                                    @Valid @RequestBody SendGemsRequest sendGemsRequest,
                                    Authentication auth ) {
        String username = (String) auth.getPrincipal();
        sendGemsService.sendGemsToUser(sendToUserId, username, sendGemsRequest);
    }


//    @GetMapping("/{wish_id}")
//    public int fetchDonationsByWish(@PathVariable("wish_id") Long wishId) {
//        return donationService.fetchDonationsByWish(wishId);
//    }
//
//    @GetMapping("/{user_id}")
//    public int fetchDonationsByUser(@PathVariable("user_id") Long userId) {
//        return donationService.fetchDonationsByUser(userId);
//    }
}
