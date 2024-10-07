package com.example.dream_shops.service.order;

import com.example.dream_shops.dto.OrderDto;
import com.example.dream_shops.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    //hàm map sang DTO

    OrderDto convertToDto(Order order);
}
