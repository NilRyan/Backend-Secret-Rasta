package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.SendGemsRequest;
import com.rastatech.secretrasta.exceptions.NotEnoughGemsException;
import com.rastatech.secretrasta.model.SendGemsEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.SendGemRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendGemsServiceImpl implements SendGemsService {

    private final UserRepository userRepository;
    private final SendGemRepository sendGemRepository;

    @Override
    @Transactional
    public void sendGemsToUser(Long sendToUserId, String username, SendGemsRequest sendGemsRequest) {
        UserEntity toUser = userRepository.findById(sendToUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity fromUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (toUser.equals(fromUser)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        int fromUserRastaGems = fromUser.getRastaGemsBalance();
        int sendAmount = sendGemsRequest.getAmount();
        SendGemsEntity newSendGems = SendGemsEntity.builder()
                .sendGemFrom(fromUser)
                .sendGemTo(toUser)
                .amount(sendAmount)
                .build();
        if (sendAmount > fromUserRastaGems) throw new NotEnoughGemsException();
        toUser.addBalance(sendAmount);
        fromUser.decreaseBalance(sendAmount);
        sendGemRepository.save(newSendGems);
    }

    @Override
    public List<SendGemsEntity> fetchSendGemsTransactions(String username) {
        return sendGemRepository.findByUser_Username(username);
    }
}
