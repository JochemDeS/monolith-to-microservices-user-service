package com.example.userservice.application.user;


import com.example.userservice.domain.User;

public interface SaveUserPort {
    User save(User user);
}
