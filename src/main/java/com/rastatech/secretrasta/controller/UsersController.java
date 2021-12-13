package com.rastatech.secretrasta.controller;

import com.rastatech.secretrasta.dto.request.UpdateUserRequest;
import com.rastatech.secretrasta.dto.response.UserDetailsResponse;
import com.rastatech.secretrasta.dto.response.UserResponse;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

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
    @ApiOperation(value = "Fetch the current Rastagem balance of the user",
            notes = "Use this api to fetch the current Rastagem balance of the user")
    public Map<String, Integer> fetchRastagemBalance(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        int balance = userService.fetchBalance(userId);
        return Collections.singletonMap("rasta_gems_balance", balance);
    }

    @GetMapping("/{user_id}")
    @ApiOperation(value = "Fetch user details",
            notes = "Use this api to fetch user details with shape as indicated by the response body below")
    public UserDetailsResponse fetchUser(@PathVariable("user_id") Long userId) {
        return convertToUserDetailsResponse(userService.fetchUser(userId));
    }

    @GetMapping("/own")
    @ApiOperation(value = "Fetch the current user details",
            notes = "Use this api to fetch the current authenticated user details with shape as indicated by the response body below")
    public UserResponse fetchOwnUser(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        return convertToResponse(userService.fetchUser(userId));
    }

    @PutMapping
    @ApiOperation(value = "Edit any or all of the details of the current user",
            notes = "Use this api to edit any or all of the details of the current authenticated user")
    public UserResponse updateUser(Authentication auth, @RequestBody UpdateUserRequest updateUserRequest) {
        String username = (String) auth.getPrincipal();
        Long userId = userService.fetchUserByUsername(username).getUserId();
        return convertToResponse(userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping
    @ApiOperation(value = "Delete the current user",
            notes = "Use this api to remove the current user from the database")
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
