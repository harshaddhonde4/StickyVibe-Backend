package com.eazybytes.StickyVibe.service.impl;

import com.eazybytes.StickyVibe.constants.ApplicationConstants;
import com.eazybytes.StickyVibe.dto.OrderRequestDto;
import com.eazybytes.StickyVibe.dto.OrderResponseDto;
import com.eazybytes.StickyVibe.entity.Customer;
import com.eazybytes.StickyVibe.entity.Order;
import com.eazybytes.StickyVibe.entity.OrderItem;
import com.eazybytes.StickyVibe.entity.Product;
import com.eazybytes.StickyVibe.exception.ResourceNotFoundException;
import com.eazybytes.StickyVibe.repository.OrderRepository;
import com.eazybytes.StickyVibe.repository.ProductRepository;
import com.eazybytes.StickyVibe.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.eazybytes.StickyVibe.dto.OrderItemResponseDto;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService
{

    public final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProfileServiceImpl profileServiceImpl;

    @Override
    public void createOrder(OrderRequestDto orderRequest) {
        Customer customer = profileServiceImpl.getAuthenticatedCustomer();
        // Create Order
        Order order = new Order();
        order.setCustomer(customer);
        BeanUtils.copyProperties(orderRequest, order);
        order.setOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);
        // Map OrderItems
        List<OrderItem> orderItems = orderRequest.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID",
                            item.productId().toString()));
            orderItem.setProduct(product);
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(item.price());
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        orderRepository.save(order);

    }

    @Override
    public List<OrderResponseDto> getCustomerOrders()
    {
        Customer customer = profileServiceImpl.getAuthenticatedCustomer();
        List<Order> orders = orderRepository.findOrderByCustomerNative(customer.getCustomerId());
        return orders.stream().map(this::mapToOrderResponseDTO).collect(Collectors.toList());
    }

    /**
     * Map Order entity to OrderResponseDto
     */
    private OrderResponseDto mapToOrderResponseDTO(Order order) {
        // Map Order Items
        List<OrderItemResponseDto> itemDTOs = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponseDTO)
                .collect(Collectors.toList());
        OrderResponseDto orderResponseDto = new OrderResponseDto(order.getOrderId()
                , order.getOrderStatus(), order.getTotalPrice(), order.getCreatedAt().toString()
                , itemDTOs);
        return orderResponseDto;
    }

    /**
     * Map OrderItem entity to OrderItemResponseDto
     */
    private OrderItemResponseDto mapToOrderItemResponseDTO(OrderItem orderItem) {
        OrderItemResponseDto itemDTO = new OrderItemResponseDto(
                orderItem.getProduct().getName(), orderItem.getQuantity(),
                orderItem.getPrice(), orderItem.getProduct().getImageUrl());
        return itemDTO;
    }

    @Override
    public List<OrderResponseDto> getAllPendingOrders() {
        List<Order> list = orderRepository.findOrderByStatusNative(ApplicationConstants.ORDER_STATUS_CREATED);
        return list.stream().map(this::mapToOrderResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long orderId, String orderStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String updatedBy = authentication.getName();
        orderRepository.updateOrderStatus(orderId, orderStatus, updatedBy);
    }
}

