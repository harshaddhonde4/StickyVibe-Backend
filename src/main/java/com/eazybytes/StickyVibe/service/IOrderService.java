package com.eazybytes.StickyVibe.service;

import com.eazybytes.StickyVibe.dto.OrderRequestDto;

public interface IOrderService
{
    void createOrder(OrderRequestDto orderRequestDto);
}
