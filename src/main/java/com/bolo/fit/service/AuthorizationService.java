package com.bolo.fit.service;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.dto.request.CreateSessionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return userService.findUserByEmail(s);
        } catch (ApiErrorException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void createSession(CreateSessionRequestDTO createSessionRequestDTO){
        UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(createSessionRequestDTO.getUserEmail(), createSessionRequestDTO.getUserPassword());
        var auth = authenticationManager.authenticate(userNamePassword);
    }
}
