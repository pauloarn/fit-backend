package com.bolo.fit.config;

import com.bolo.fit.controller.handler.CustomAccessDeniedHandler;
import com.bolo.fit.filters.AuthFilter;
import com.bolo.fit.filters.LogFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LogFilter loggerFilter;
    private final AuthFilter authFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests(auth ->
                         auth
                            .antMatchers("/session").permitAll()
                            .antMatchers("/user").permitAll()
//                            .antMatchers("/user").hasAnyRole(RoleEnum.ADMIN.toString(), RoleEnum.PERSONAL_TRAINER.toString())
                            .anyRequest().authenticated()
            )
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(loggerFilter, AuthFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
