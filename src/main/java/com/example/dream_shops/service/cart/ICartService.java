package com.example.dream_shops.service.cart;

import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
