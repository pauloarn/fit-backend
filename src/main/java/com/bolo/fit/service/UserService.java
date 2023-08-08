package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.Users;
import com.bolo.fit.repository.UserRepository;
import com.bolo.fit.service.dto.request.CreateUserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractServiceRepo<UserRepository, Users, Long> {

    public UserService(UserRepository repository) {
        super(repository);
    }

    public Users findUserByEmail(String userEmail) throws ApiErrorException {
        return repository.findUsersByEmail(userEmail).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.USER_NOT_FOUND));
    }

    public void createUser(CreateUserRequestDTO createUserRequest) throws ApiErrorException {
        Optional<Users> userAux = repository.findUsersByEmail(createUserRequest.getEmail());
        if(userAux.isPresent()){
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.USER_ALREADY_EXISTS);
        }
        Users newUser = new Users();
        newUser.setRole(createUserRequest.getRole());
    }
}
