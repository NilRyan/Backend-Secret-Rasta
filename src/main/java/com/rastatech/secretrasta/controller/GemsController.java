package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.AddBalanceRequest;
import com.rastatech.secretrasta.dto.request.SendGemsRequest;
import com.rastatech.secretrasta.service.AddBalanceService;
import com.rastatech.secretrasta.service.SendGemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rastagem")
@RequiredArgsConstructor
public class GemsController {

    private final AddBalanceService addBalanceService;
    private final SendGemsService sendGemsService;

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

}
