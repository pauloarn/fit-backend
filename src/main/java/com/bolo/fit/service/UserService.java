package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.User;
import com.bolo.fit.repository.UserRepository;
import com.bolo.fit.service.dto.request.CreateUserRequestDTO;
import com.bolo.fit.service.dto.response.UserDetailResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserService extends AbstractServiceRepo<UserRepository, User, Long> {

    public UserService(UserRepository repository) {
        super(repository);
    }

    public User findUserByEmail(String userEmail) throws ApiErrorException {
        log.info("Buscando usuário por email");
        return repository.findUsersByEmail(userEmail).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.USER_NOT_FOUND));
    }

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    public UserDetailResponseDTO createUser(CreateUserRequestDTO createUserRequest) throws ApiErrorException {
        log.info("Verifying if email is already registered");
        Optional<User> userAux = repository.findUsersByEmail(createUserRequest.getEmail());
        if(userAux.isPresent()){
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.USER_ALREADY_EXISTS);
        }
        log.info("Creating new user");
        String encryptedPass = new BCryptPasswordEncoder().encode(createUserRequest.getPassword());
        User newUser = new User();
        newUser.setRole(createUserRequest.getRole());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setName(createUserRequest.getName());
        newUser.setPassword(encryptedPass);
        repository.save(newUser);
        log.info("User created");
        return new UserDetailResponseDTO(newUser);
    }
}
