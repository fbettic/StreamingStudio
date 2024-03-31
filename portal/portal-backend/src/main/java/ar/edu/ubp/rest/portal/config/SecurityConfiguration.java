package ar.edu.ubp.rest.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        @Autowired
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        @Autowired
        private final AuthenticationProvider authProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                authRequest -> authRequest
                                                                // .requestMatchers(HttpMethod.GET).permitAll()
                                                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                                                .requestMatchers("api/v1/auth/**").permitAll()
                                                                .requestMatchers("api/v1/admin/**")
                                                                .hasAuthority(Role.ADMINISTRATOR.name())
                                                                .anyRequest()
                                                                .authenticated())
                                .sessionManagement(
                                                sessionManagement -> sessionManagement
                                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}
