package com.example.dream_shops.service.order;

import com.example.dream_shops.dto.OrderDto;
import com.example.dream_shops.enums.OrderStatus;
import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.Order;
import com.example.dream_shops.model.OrderItem;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.OrderRepository;
import com.example.dream_shops.repository.ProductRepository;
import com.example.dream_shops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setOrderTotalAmount(calculateOrderTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId()); // clear car
        // t
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = Order.builder()
                .orderDate(java.time.LocalDate.now())
                //.orderTotalAmount(BigDecimal.ZERO)
                .orderStatus(OrderStatus.PENDING)
                .user(cart.getUser())
                .build();
        return orderRepository.save(order);
    }

    private List<OrderItem> createOrderItems(Order  order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();
        }).toList();
    }

    private BigDecimal calculateOrderTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map((orderItem) -> orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);

        //convert sang DTO
        return orders.stream()
                .map(this :: convertToDto)
                .toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
