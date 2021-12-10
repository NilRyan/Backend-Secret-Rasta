package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.AddBalanceRequest;
import com.rastatech.secretrasta.dto.DonationRequest;
import com.rastatech.secretrasta.service.AddBalanceService;
import com.rastatech.secretrasta.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/addbalance")
@RequiredArgsConstructor
public class AddBalanceController {

    private final AddBalanceService addBalanceService;

    @PostMapping
    public void addBalance(@Valid @RequestBody AddBalanceRequest addBalanceRequest, Authentication auth) {
        String username = (String) auth.getPrincipal();
        addBalanceService.addBalance(username, addBalanceRequest);
    }
}
