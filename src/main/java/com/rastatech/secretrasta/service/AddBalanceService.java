package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.AddBalanceRequest;

public interface AddBalanceService {
    void addBalance(String username, AddBalanceRequest addBalance);
}
