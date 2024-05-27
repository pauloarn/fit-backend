package com.bolo.fit.config;

import com.bolo.fit.controller.handler.CustomAccessDeniedHandler;
import com.bolo.fit.controller.handler.CustomAuthenticationEntryPointHandler;
import com.bolo.fit.filters.AuthFilter;
import com.bolo.fit.filters.LogFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableWebSecurity
public class SecurityConfig {
    private final LogFilter loggerFilter;
    private final AuthFilter authFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loggerFilter, AuthFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/session/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/exercises/image").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .exceptionHandling(ex -> {
                    ex.accessDeniedHandler(customAccessDeniedHandler);
                    ex.authenticationEntryPoint(customAuthenticationEntryPointHandler);
                });

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
