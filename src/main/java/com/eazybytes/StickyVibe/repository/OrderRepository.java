package com.eazybytes.StickyVibe.repository;

import com.eazybytes.StickyVibe.entity.Order;
import com.eazybytes.StickyVibe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
{
}