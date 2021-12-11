package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.SendGemsRequest;
import com.rastatech.secretrasta.model.SendGemsEntity;
import java.util.List;

public interface SendGemsService {
    void sendGemsToUser(Long sendToUserId, String username, SendGemsRequest sendGemsRequest);
    List<SendGemsEntity> fetchSendGemsTransactions(String username);
}
