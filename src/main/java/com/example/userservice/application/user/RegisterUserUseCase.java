package com.example.userservice.application.user;

import com.example.userservice.application.cart.CreateCart;
import com.example.userservice.application.common.UseCase;
import com.example.userservice.domain.User;
import com.example.userservice.security.EncryptionService;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase implements UseCase<RegisterUserRequest, User> {
    private final EncryptionService encryptionService;
    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;
    private final CreateCart createCart;

    public RegisterUserUseCase(EncryptionService encryptionService,
                               GetUserPort getUserPort,
                               SaveUserPort saveUserPort,
                               CreateCart createCart) {
        this.encryptionService = encryptionService;
        this.getUserPort = getUserPort;
        this.saveUserPort = saveUserPort;
        this.createCart = createCart;
    }

    public User handle(RegisterUserRequest request) {
        final var encryptedPassword = encryptionService.encrypt(request.password());
        final var user = User.builder()
                .username(request.username())
                .password(encryptedPassword)
                .email(request.email())
                .name(request.name())
                .build();

        getUserPort.byUsername(user.username())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("Username already exists");
                });

        getUserPort.byEmail(user.email())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        final var savedUser = saveUserPort.save(user);
        createCart.create(savedUser.id());
        return savedUser;
    }
}
