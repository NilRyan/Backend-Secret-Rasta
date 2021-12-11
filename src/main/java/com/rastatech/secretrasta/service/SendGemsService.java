package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.SendGemsRequest;

public interface SendGemsService {
    void sendGemsToUser(Long sendToUserId, String username, SendGemsRequest sendGemsRequest);
}
