package com.rastatech.secretrasta.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rastatech.secretrasta.dto.request.NewUserRequest;
import com.rastatech.secretrasta.dto.request.RoleToUserRequest;
import com.rastatech.secretrasta.dto.response.UserResponse;
import com.rastatech.secretrasta.exceptions.FieldNotAvailableException;
import com.rastatech.secretrasta.model.Role;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.UserRepository;
import com.rastatech.secretrasta.security.JwtUtilities;
import com.rastatech.secretrasta.service.RoleService;
import com.rastatech.secretrasta.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @PostMapping("/signup")
    @ApiOperation(value = "Create a user and store in database",
            notes = "Provide the request body as specified below. Username, phone_number (11 digits), email must be unique")
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUserRequest user) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            if (userRepository.existsByUsername(user.getUsername()))
                resultMap.put("username", String.format("Username %s is not available", user.getUsername()));
            if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))
                resultMap.put("phone_number", String.format("Phone number %s is not available", user.getPhoneNumber()));
            if (userRepository.existsByEmail(user.getEmail()))
                resultMap.put("email", String.format("Email %s is not available", user.getEmail()));
            if (resultMap.size() != 0)
                throw new FieldNotAvailableException();

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(modelMapper.map(user, UserEntity.class));
            roleService.addRoleToUser(user.getUsername(), "ROLE_USER");
        } catch (FieldNotAvailableException e) {
            return ResponseEntity.badRequest().body(resultMap);
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/signup").toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/refresh/token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = jwtUtilities.decodeJWT(authorizationHeader);
                String username = decodedJWT.getSubject();
                UserEntity user = userService.fetchUserByUsername(username);
                String access_token = jwtUtilities.buildAccessJwt(user);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    private UserResponse convertToResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserRequest form) {
        roleService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
