package com.example.dream_shops.controller;

import com.example.dream_shops.dto.OrderDto;
import com.example.dream_shops.model.Order;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try{
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order created successfully!")
                    .data(orderDto)
                    .build());
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/order/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try{
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order retrieved successfully!")
                    .data(order)
                    .build());
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/order/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try{
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order retrieved successfully!")
                    .data(order)
                    .build());
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
}
