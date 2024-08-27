package com.example.userservice.infrastructure.persistence;


import com.example.userservice.application.user.GetUserPort;
import com.example.userservice.application.user.SaveUserPort;
import com.example.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSqlPersistenceAdapter implements SaveUserPort, GetUserPort {
    private final UserRepository userRepository;

    public UserSqlPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        final var userEntity = UserMapper.toEntity(user);
        final var savedUserEntity = userRepository.save(userEntity);
        return UserMapper.toDomain(savedUserEntity);
    }

    @Override
    public Optional<User> byUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> byEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(UserMapper::toDomain);
    }
}
