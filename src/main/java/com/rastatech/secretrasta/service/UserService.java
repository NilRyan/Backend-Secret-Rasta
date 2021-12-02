package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.dto.UpdateUserRequest;
import com.rastatech.secretrasta.model.Role;
import com.rastatech.secretrasta.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> fetchUsers();
    UserEntity fetchUser(Long userId);
    UserEntity updateUser(Long userId, UpdateUserRequest user);
    void deleteUser(Long userId);
    UserEntity fetchUserByUsername(String username);
}
