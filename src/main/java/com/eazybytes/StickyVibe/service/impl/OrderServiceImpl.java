package com.eazybytes.StickyVibe.service.impl;

import com.eazybytes.StickyVibe.constants.ApplicationConstants;
import com.eazybytes.StickyVibe.dto.OrderRequestDto;
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
import org.springframework.stereotype.Service;

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
}

