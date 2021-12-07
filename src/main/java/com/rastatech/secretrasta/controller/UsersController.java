package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.UpdateUserRequest;
import com.rastatech.secretrasta.dto.UserDetailsResponse;
import com.rastatech.secretrasta.dto.UserResponse;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;

//    @GetMapping
//    public List<UserResponse> fetchUsers(@RequestParam Optional<Integer> page,
//                                         @RequestParam Optional<Integer> limit,
//                                         @RequestParam Optional<String> sort,
//                                         @RequestParam Optional<Integer> direction) {
//        Pageable pageable = PageRequest.of(page.orElseGet(() -> 0),
//                limit.orElseGet(() -> 10),
//                Sort.by(sort.orElseGet(() -> "updatedAt"))
//                        .descending());
//        List<UserEntity> users = userService.fetchUsers();
//        return users.stream().map(this::convertToResponse).collect(Collectors.toList());
//    }

    @GetMapping("/balance")
    public Map<String, Integer> fetchRastagemBalance(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        int balance = userService.fetchBalance(userId);
        return Collections.singletonMap("rasta_gems_balance", balance);
    }

    @GetMapping("/{user_id}")
    public UserDetailsResponse fetchUser(@PathVariable("user_id") Long userId) {
        return convertToUserDetailsResponse(userService.fetchUser(userId));
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

    private UserDetailsResponse convertToUserDetailsResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDetailsResponse.class);
    }
}
