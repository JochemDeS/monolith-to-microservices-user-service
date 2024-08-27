package com.example.userservice.application.user;

import com.example.userservice.domain.User;

import java.util.Optional;

public interface GetUserPort {
    Optional<User> byUsername(String username);
    Optional<User> byEmail(String email);
}
