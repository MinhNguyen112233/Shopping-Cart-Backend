package com.example.dream_shops.service.cart;

import com.example.dream_shops.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public interface ICartItemService {
    void addCartItem(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
