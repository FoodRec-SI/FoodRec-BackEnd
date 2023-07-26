package com.foodrec.backend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .xssProtection(xXssConfig -> xXssConfig
                                .headerValue(XXssProtectionHeaderWriter.HeaderValue.DISABLED))
                        .contentSecurityPolicy(contentSecurityPolicyConfig -> contentSecurityPolicyConfig
                                .policyDirectives("script-src 'self'; img-src 'self'")))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/private/**").hasAnyRole(MODERATOR,MEMBER)
                        .requestMatchers("/api/moderator/**").hasRole(MODERATOR)
                        .requestMatchers("/api/member/**").hasAnyRole(MEMBER)
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .build();
    }

    public static final String MODERATOR = "MODERATOR";
    public static final String MEMBER = "MEMBER";

}
