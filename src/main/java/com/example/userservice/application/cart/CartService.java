package com.example.userservice.application.cart;

import com.example.userservice.domain.CartId;
import com.example.userservice.domain.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService implements CreateCart {
    @Value("${microservices.cart.url}")
    private String cartServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public CartId create(UserId userId) {
        final var request = CreateCartRequest.builder()
                .userId(userId.value().intValue())
                .build();

        final var response = restTemplate.postForEntity(
                String.format("%s/cart/create", cartServiceUrl),
                request,
                CartId.class);

        return response.getBody();
    }
}
