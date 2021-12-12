package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.request.UpdateUserRequest;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserEntity> fetchUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity fetchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public UserEntity updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        UserEntity user = fetchUser(userId);
        modelMapper.map(updateUserRequest, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        userRepository.deleteById(userId);
    }

    @Override
    public UserEntity fetchUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public int fetchBalance(Long userId) {
        return fetchUser(userId).getRastaGemsBalance();
    }
}
