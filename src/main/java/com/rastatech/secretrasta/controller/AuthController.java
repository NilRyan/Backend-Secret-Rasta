//package com.rastatech.secretrasta.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
//        return null;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//        return null;
//    }
//
//    @PostMapping("/refresh/token")
//    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//        return null;
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
//        return null;
//    }
//}
