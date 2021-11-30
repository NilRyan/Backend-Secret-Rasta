package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateUserRequest;
import com.rastatech.secretrasta.model.UserEntity;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<UserEntity> fetchUsers() {
        return null;
    }

    @Override
    public UserEntity fetchUser(Long userId) {
        return null;
    }

    @Override
    public UserEntity updateUser(Long userId, UpdateUserRequest user) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
