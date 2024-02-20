package ar.edu.ubp.rest.portal.controller;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.AdministratorSignupRequest;
import ar.edu.ubp.rest.portal.dto.AdvertiserSignupRequest;
import ar.edu.ubp.rest.portal.dto.AuthResponse;
import ar.edu.ubp.rest.portal.dto.LoginRequest;
import ar.edu.ubp.rest.portal.dto.SubscriberSignupRequest;
import ar.edu.ubp.rest.portal.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("subscriber/signup")
    public ResponseEntity<AuthResponse> subscriberSignup(@RequestBody SubscriberSignupRequest request) {
        return ResponseEntity.ok(authService.subscriberSignup(request));
    }

    @PostMapping("admin/signup")
    public ResponseEntity<AuthResponse> adminSignup(@RequestBody AdministratorSignupRequest request) {
        
        return ResponseEntity.ok(authService.administrarorSignup(request));
    }

    @PostMapping("advertiser/signup")
    public ResponseEntity<AuthResponse> advertiserSignup(@RequestBody AdvertiserSignupRequest request) {
        
        return ResponseEntity.ok(authService.advertiserSignup(request));
    }
}
