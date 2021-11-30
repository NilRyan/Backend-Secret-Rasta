package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateUserRequest;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<UserEntity> fetchUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity fetchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public UserEntity updateUser(Long userId, UpdateUserRequest user) {

    }

    @Override
    public void deleteUser(Long userId) {

    }
}
