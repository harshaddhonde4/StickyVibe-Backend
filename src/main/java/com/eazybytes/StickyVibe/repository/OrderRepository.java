package com.eazybytes.StickyVibe.repository;

import com.eazybytes.StickyVibe.entity.Customer;
import com.eazybytes.StickyVibe.entity.Order;
import com.eazybytes.StickyVibe.entity.Product;
import com.eazybytes.StickyVibe.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);
    List<Order> findByOrderStatus(String orderStatus);
}