package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.UpdateUserRequest;
import com.rastatech.secretrasta.dto.UserResponse;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<UserResponse> fetchUsers() {
        List<UserEntity> users = userService.fetchUsers();
        return users.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @GetMapping("/balance")
    public Map<String, Integer> fetchRastagemBalance(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        int balance = userService.fetchBalance(userId);
        return Collections.singletonMap("rasta_gems_balance", balance);
    }

    @GetMapping("/{user_id}")
    public UserResponse fetchUser(@PathVariable("user_id") Long userId) {
        return convertToResponse(userService.fetchUser(userId));
    }

    @GetMapping("/own")
    public UserResponse fetchOwnUser(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        return convertToResponse(userService.fetchUser(userId));
    }

    @PutMapping
    public UserResponse updateUser(Authentication auth, @RequestBody UpdateUserRequest updateUserRequest) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        return convertToResponse(userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping
    public void deleteUser(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        userService.deleteUser(userId);
    }

    private UserResponse convertToResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

}
