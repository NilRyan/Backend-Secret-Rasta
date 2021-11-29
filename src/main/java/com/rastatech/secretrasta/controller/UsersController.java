package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.UpdateUserRequest;
import com.rastatech.secretrasta.dto.UserResponse;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private UserService userService;
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserResponse> fetchUsers() {
        List<UserEntity> users = userService.fetchUsers();
        return users.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @GetMapping("/{user_id}")
    public UserResponse fetchUser(@PathVariable("user_id") Long userId) {
        return convertToResponse(userService.fetchUser(userId));
    }

    @PutMapping("/{user_id}")
    public UserResponse updateUser(@PathVariable("user_id") Long userId, UpdateUserRequest updateUserRequest) {
       return convertToResponse(userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);
    }

    private UserResponse convertToResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

}
