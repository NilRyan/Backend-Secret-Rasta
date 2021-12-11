package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.AddBalanceRequest;
import com.rastatech.secretrasta.model.AddBalanceEntity;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.AddBalanceRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddBalanceServiceImpl implements AddBalanceService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AddBalanceRepository addBalanceRepository;

    @Override
    @Transactional
    public void addBalance(String username, AddBalanceRequest addBalance) {
        UserEntity user = userService.fetchUserByUsername(username);
        user.addBalance(addBalance.getAmount());
        userRepository.save(user);
        AddBalanceEntity addBalanceEntity = new AddBalanceEntity();
        addBalanceEntity.setAmount(addBalance.getAmount());
        addBalanceEntity.setUser(user);
        addBalanceRepository.save(addBalanceEntity);
    }

    @Override
    public List<AddBalanceEntity> fetchAddBalanceTransactions(String username) {
        return addBalanceRepository.findByUser_Username(username);
    }

}
