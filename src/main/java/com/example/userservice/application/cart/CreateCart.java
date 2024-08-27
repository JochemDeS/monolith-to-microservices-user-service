package com.example.userservice.application.cart;

import com.example.userservice.domain.CartId;
import com.example.userservice.domain.UserId;

public interface CreateCart {
    CartId create(UserId userId);
}
