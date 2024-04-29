package ar.edu.ubp.rest.portal.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.request.AdministratorRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AuthUserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.enums.ServiceType;
import ar.edu.ubp.rest.portal.models.users.Administrator;
import ar.edu.ubp.rest.portal.models.users.Advertiser;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.models.users.Subscriber;
import ar.edu.ubp.rest.portal.repositories.AdmistratorRepository;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;
import ar.edu.ubp.rest.portal.repositories.SubscriberRepository;
import ar.edu.ubp.rest.portal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AdmistratorRepository admistratorRepository;
    @Autowired
    private final AdvertiserRepository advertiserRepository;
    @Autowired
    private final SubscriberRepository subscriberRepository;
    @Autowired
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(AuthUserRequestDTO request) {
        CustomUserDetails user = userRepository.getUserByEmail(request.getEmail());

        String token = jwtService.getToken(user);

        return AuthResponseDTO.builder()
                .id(user.getId())
                .token(token)
                .email(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponseDTO createSubscriber(SubscriberRequestDTO request) {

        LocalDate birth = LocalDate.parse(request.getBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Subscriber user = Subscriber.subscriberBuilder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .birth(birth)
                .validated(true)
                .build();

        user.setId(subscriberRepository.createSubscriber(user).getId());

        return buildAuthResponse(user);
    }

    public AuthResponseDTO createAdministrator(AdministratorRequestDTO request) {
        Administrator user = Administrator.administratorBuilder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user.setId(admistratorRepository.createAdministrator(user).getId());

        return buildAuthResponse(user);
    }

    public AuthResponseDTO createAdvertiser(AdvertiserRequestDTO request) {
        ServiceType serviceType = ServiceType.valueOf(request.getServiceType());

        Advertiser user = Advertiser.advertiserBuilder()
                .email(request.getEmail())
                .agentFirstname(request.getAgentFirstname())
                .agentLastname(request.getAgentLastname())
                .companyName(request.getCompanyName())
                .bussinesName(request.getBussinesName())
                .phone("1111111111111")
                .serviceType(serviceType)
                .build();

        if (Objects.equals(serviceType, ServiceType.REST) || Objects.equals(serviceType, ServiceType.SOAP)) {
            user.setApiUrl(request.getApiUrl());
            user.setAuthToken(request.getAuthToken());
        } else if (Objects.equals(serviceType, ServiceType.ACCOUNT)) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        } else {
            System.out.println("ServiceType desconocido: " + serviceType);
        }

       

        user.setId(advertiserRepository.createAdvertiser(user).getAdvertiserId());
        return buildAuthResponse(user);
    }

    private AuthResponseDTO buildAuthResponse(CustomUserDetails user) {
       
        return AuthResponseDTO.builder()
                .id(user.getId())
                .token(jwtService.getToken(user))
                .email(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
