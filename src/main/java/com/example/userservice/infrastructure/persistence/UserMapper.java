package com.example.userservice.infrastructure.persistence;

import com.example.userservice.domain.Name;
import com.example.userservice.domain.User;
import com.example.userservice.domain.UserId;

public class UserMapper {
    public static User toDomain(UserEntity userEntity) {
        return User.builder()
                .id(UserId.builder()
                        .value(userEntity.id())
                        .build())
                .username(userEntity.username())
                .password(userEntity.password())
                .email(userEntity.email())
                .name(Name.builder()
                        .firstName(userEntity.firstName())
                        .lastName(userEntity.lastName())
                        .build())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity.Builder()
                .id(user.id() != null ? user.id().value() : null)
                .username(user.username())
                .password(user.password())
                .email(user.email())
                .firstName(user.name().firstName())
                .lastName(user.name().lastName())
                .build();
    }
}
