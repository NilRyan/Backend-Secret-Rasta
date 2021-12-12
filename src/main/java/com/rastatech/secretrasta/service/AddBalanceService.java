package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.AddBalanceRequest;
import com.rastatech.secretrasta.model.AddBalanceEntity;

import java.util.List;

public interface AddBalanceService {
    void addBalance(String username, AddBalanceRequest addBalance);
    List<AddBalanceEntity> fetchAddBalanceTransactions(String username);
}
