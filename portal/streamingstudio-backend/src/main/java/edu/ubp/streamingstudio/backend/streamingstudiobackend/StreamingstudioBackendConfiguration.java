package edu.ubp.streamingstudio.backend.streamingstudiobackend;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class StreamingstudioBackendConfiguration {
    
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();       
        source.registerCorsConfiguration("/**", config);
        
        var bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}