package com.eazybytes.StickyVibe.service;

import com.eazybytes.StickyVibe.dto.OrderRequestDto;
import com.eazybytes.StickyVibe.dto.OrderResponseDto;
import com.eazybytes.StickyVibe.entity.Order;

import java.util.List;

public interface IOrderService
{
    void createOrder(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getCustomerOrders();
    List<OrderResponseDto> getAllPendingOrders();
    void updateOrderStatus(Long orderId, String orderStatus);
}
