package com.example.userservice.application.common;

public interface UseCase<T, R> {
    R handle (T request);
}
