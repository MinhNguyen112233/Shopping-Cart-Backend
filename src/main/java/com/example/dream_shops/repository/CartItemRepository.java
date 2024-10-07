package com.example.dream_shops.repository;

import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCart(Cart cart);
}
