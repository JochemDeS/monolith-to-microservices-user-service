package com.example.userservice.domain;

public record UserId(Long value) {
    public UserId(Builder builder) {
        this(builder.value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long value;

        public Builder value(Long value) {
            this.value = value;
            return this;
        }

        public UserId build() {
            return new UserId(this);
        }
    }
}
