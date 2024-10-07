package com.example.dream_shops.controller;

import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.User;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.cart.ICartItemService;
import com.example.dream_shops.service.cart.ICartService;
import com.example.dream_shops.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;

    private final ICartService cartService;
    private final UserService userService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try{
            User user = userService.getAuthenticationUser();
            Cart cartId = cartService.initializeNewCart(user);

            cartItemService.addCartItem(cartId.getId(), productId, quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Item added to cart!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(ApiResponse.builder().message(e.getMessage()).build());
        }
    }

    @DeleteMapping("{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId) {
        try{
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Item removed from cart!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(ApiResponse.builder().message(e.getMessage()).build());
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer quantity) {
        try{
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Item quantity updated!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(ApiResponse.builder().message(e.getMessage()).build());
        }
    }
}
