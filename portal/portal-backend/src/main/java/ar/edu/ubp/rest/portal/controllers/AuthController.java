package ar.edu.ubp.rest.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.request.AdministratorRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AuthRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AuthController {
    private final AuthService authService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {

        this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return ResponseEntity.ok(authService.login(request));

    }

    @PostMapping("subscribers")
    public ResponseEntity<AuthResponseDTO> createSubscriber(@RequestBody SubscriberRequestDTO request) {
        return ResponseEntity.ok(authService.createSubscriber(request));

    }

    @PostMapping("administrators")
    public ResponseEntity<AuthResponseDTO> createAdministrator(@RequestBody AdministratorRequestDTO request) {

        return ResponseEntity.ok(authService.createAdministrator(request));
    }

}
