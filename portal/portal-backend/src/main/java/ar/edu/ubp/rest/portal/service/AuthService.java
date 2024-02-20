package ar.edu.ubp.rest.portal.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.enums.ServiceType;
import ar.edu.ubp.rest.portal.model.Administrator;
import ar.edu.ubp.rest.portal.model.Advertiser;
import ar.edu.ubp.rest.portal.model.Subscriber;
import ar.edu.ubp.rest.portal.repository.AdministratorRepository;
import ar.edu.ubp.rest.portal.repository.AdvertiserRepository;
import ar.edu.ubp.rest.portal.repository.SubscriberRepository;
import ar.edu.ubp.rest.portal.repository.UserRepository;
import ar.edu.ubp.rest.portal.dto.AdministratorSignupRequest;
import ar.edu.ubp.rest.portal.dto.AdvertiserSignupRequest;
import ar.edu.ubp.rest.portal.dto.AuthResponse;
import ar.edu.ubp.rest.portal.dto.LoginRequest;
import ar.edu.ubp.rest.portal.dto.SubscriberSignupRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SubscriberRepository subscriberRepository;
    private final AdvertiserRepository advertiserRepository;
    private final AdministratorRepository administratorRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    // Método para manejar el inicio de sesión
    public AuthResponse login(LoginRequest request) {
        // Obtener el rol del usuario por su correo electrónico
        String role = userRepository.getRoleByEmail(request.getEmail());

        // Obtener los detalles del usuario según su rol
        UserDetails user = getUserDetailsByRole(request.getEmail(), role);

        // Generar un token JWT para el usuario
        String token = jwtService.getToken(user);

        // Construir y retornar la respuesta de autenticación
        return AuthResponse.builder()
                .token(token)
                .email(user.getUsername())
                .role(role)
                .build();
    }

    // Método para registrar un nuevo suscriptor
    public AuthResponse subscriberSignup(SubscriberSignupRequest request) {
        // Parsear la fecha de nacimiento
        LocalDate birth = LocalDate.parse(request.getBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Construir un objeto de suscriptor con los datos proporcionados
        Subscriber user = Subscriber.builder()
                .email(request.getEmail())
                .name(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .birth(birth)
                .validated(true)
                .build();

        // Guardar el suscriptor en el repositorio
        subscriberRepository.save(user);

        // Generar un token JWT para el usuario y construir la respuesta de autenticación
        return buildAuthResponse(user);
    }

    // Método para registrar un nuevo administrador
    public AuthResponse administrarorSignup(AdministratorSignupRequest request) {
        // Construir un objeto de administrador con los datos proporcionados
        Administrator user = Administrator.builder()
                .email(request.getEmail())
                .name(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // Guardar el administrador en el repositorio
        administratorRepository.save(user);

        // Generar un token JWT para el usuario y construir la respuesta de autenticación
        return buildAuthResponse(user);
    }

    // Método para registrar un nuevo anunciante
    public AuthResponse advertiserSignup(AdvertiserSignupRequest request) {
        // Determinar el tipo de servicio del anunciante
        ServiceType serviceType = ServiceType.valueOf(request.getServiceType());

        // Construir un objeto de anunciante con los datos proporcionados
        Advertiser user = Advertiser.builder()
                .email(request.getEmail())
                .agentName(request.getAgentName())
                .agentLastname(request.getAgentLastname())
                .companyName(request.getCompanyName())
                .bussinesName(request.getBussinesName())
                .phone("1111111111111")
                .serviceType(serviceType)
                .build();

        // Según el tipo de servicio, establecer información adicional
        if (Objects.equals(serviceType, ServiceType.REST) || Objects.equals(serviceType, ServiceType.SOAP)) {
            user.setApiUrl(request.getApiUrl());
            user.setAuthToken(request.getAuthToken());
        } else if (Objects.equals(serviceType, ServiceType.ACCOUNT)) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        } else {
            // Manejar caso de ServiceType desconocido
            // Aquí podrías lanzar una excepción, registrar un error, etc.
            // En este ejemplo, solo se imprime un mensaje de advertencia
            System.out.println("ServiceType desconocido: " + serviceType);
        }

        // Guardar el anunciante en el repositorio
        advertiserRepository.save(user);

        // Generar un token JWT para el usuario y construir la respuesta de autenticación
        return buildAuthResponse(user);
    }

    // Método privado para obtener los detalles de usuario según su rol
    private UserDetails getUserDetailsByRole(String email, String role) {
        switch (role) {
            case "SUBSCRIBER":
                return subscriberRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            case "ADVERTISER":
                return advertiserRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            case "ADMINISTRATOR":
                return administratorRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    // Método privado para generar una respuesta de autenticación
    private AuthResponse buildAuthResponse(UserDetails user) {
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .email(user.getUsername())
                .role(user.getAuthorities().toString())
                .build();
    }
}
