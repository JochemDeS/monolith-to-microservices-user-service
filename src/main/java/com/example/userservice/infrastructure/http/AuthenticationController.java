package com.example.userservice.infrastructure.http;

import com.example.userservice.application.user.LoginUserRequest;
import com.example.userservice.application.user.RegisterUserRequest;
import com.example.userservice.application.common.UseCase;
import com.example.userservice.domain.Name;
import com.example.userservice.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "All endpoints for authentication")
public class AuthenticationController {
    private final UseCase<RegisterUserRequest, User> registerUserUseCase;
    private final UseCase<LoginUserRequest, String> loginUserUseCase;

    public AuthenticationController(UseCase<RegisterUserRequest, User> registerUserUseCase, UseCase<LoginUserRequest, String> loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterUserWriteModel request) {
        final var registerUser = RegisterUserRequest.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .name(Name.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .build())
                .build();

        registerUserUseCase.handle(registerUser);
    }

    @PostMapping("/login")
    public LoginUserReadModel login(@Valid @RequestBody LoginUserWriteModel request) {
        final var loginUser = LoginUserRequest.builder()
                .username(request.username())
                .password(request.password())
                .build();

        final var jwt = loginUserUseCase.handle(loginUser);

        return LoginUserReadModel.builder()
                .token(jwt)
                .build();
    }

    @GetMapping("/me")
    public DetailedUserModel getLoggedInUser(@AuthenticationPrincipal User user) {
        return DetailedUserModel.builder()
                .id(user.id().value().intValue())
                .firstName(user.name().firstName())
                .lastName(user.name().lastName())
                .email(user.email())
                .username(user.username())
                .build();
    }
}
