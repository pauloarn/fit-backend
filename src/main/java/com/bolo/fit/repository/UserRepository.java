package com.bolo.fit.repository;

import com.bolo.fit.model.Users;

import java.util.Optional;

public interface UserRepository extends GenericRepository<Users, Long> {
    Optional<Users> findUsersByEmail(String userEmail);
}
