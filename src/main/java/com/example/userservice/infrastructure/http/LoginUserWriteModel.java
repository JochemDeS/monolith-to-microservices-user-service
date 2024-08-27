package com.example.userservice.infrastructure.http;

import jakarta.validation.constraints.NotBlank;

public record LoginUserWriteModel(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
