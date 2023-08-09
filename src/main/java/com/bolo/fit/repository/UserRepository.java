package com.bolo.fit.repository;

import com.bolo.fit.model.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Long> {
    Optional<User> findUsersByEmail(String userEmail);
}
