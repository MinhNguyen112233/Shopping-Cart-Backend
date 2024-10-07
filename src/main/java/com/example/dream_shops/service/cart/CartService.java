package com.example.dream_shops.service.cart;

import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import com.example.dream_shops.model.User;
import com.example.dream_shops.repository.CartItemRepository;
import com.example.dream_shops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount.subtract(cart.getTotalAmount()));
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCart(cart);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Cart initializeNewCart(User user){
//        Cart newCart = new Cart();
//        Long newCartId = cartIdGenerator.incrementAndGet();
//        newCart.setId(newCartId);
//        return cartRepository.save(newCart).getId();
        return Optional
                .ofNullable(cartRepository.findByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    Long newCartId = cartIdGenerator.incrementAndGet();
                    newCart.setId(newCartId);
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
