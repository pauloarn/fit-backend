package com.bolo.fit.service;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.User;
import com.bolo.fit.service.dto.request.CreateSessionRequestDTO;
import com.bolo.fit.service.dto.response.SessionResponseDTO;
import com.bolo.fit.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return userService.findUserByEmail(s);
        } catch (ApiErrorException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public SessionResponseDTO createSession(CreateSessionRequestDTO createSessionRequestDTO) throws ApiErrorException {
        UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(createSessionRequestDTO.getUserEmail(), createSessionRequestDTO.getUserPassword());
        var auth = authenticationManager.authenticate(userNamePassword);
        var token = tokenUtils.generateToken((User) auth.getPrincipal());
        return new SessionResponseDTO(token);
    }
}
