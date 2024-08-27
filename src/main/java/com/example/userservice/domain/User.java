package com.example.userservice.domain;

import java.util.ArrayList;
import java.util.List;

public record User(
        UserId id,
        String username,
        String password,
        String email,
        Name name,
        List<Order> orders
) {
    public User(Builder builder) {
        this(builder.id, builder.username, builder.password, builder.email, builder.name, builder.orders);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserId id;
        private String username;
        private String password;
        private String email;
        private Name name;
        private List<Order> orders = new ArrayList<>();

        public Builder id(UserId id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder orders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
