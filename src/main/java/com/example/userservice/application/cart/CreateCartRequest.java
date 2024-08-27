package com.example.userservice.application.cart;

public record CreateCartRequest(int userId) {
    public CreateCartRequest(Builder builder) {
        this(builder.userId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int userId;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public CreateCartRequest build() {
            return new CreateCartRequest(this);
        }
    }
}
