package com.example.dream_shops.controller;

import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final ICartService cartService;


    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Cart retrieved successfully!")
                    .data(cart)
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(ApiResponse.builder().message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{cartId}/clear-cart")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try{
            cartService.clearCart(cartId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Cart deleted successfully!")
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(ApiResponse.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping("/{cartId}/total-amount")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try{
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Total amount retrieved successfully!")
                    .data(totalPrice)
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(ApiResponse.builder().message(e.getMessage()).build());
        }
    }
}
