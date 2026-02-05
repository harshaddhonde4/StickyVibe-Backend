package com.eazybytes.StickyVibe.repository;

import com.eazybytes.StickyVibe.entity.Customer;
import com.eazybytes.StickyVibe.entity.Order;
import com.eazybytes.StickyVibe.entity.Product;
import com.eazybytes.StickyVibe.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);

  List<Order> findByOrderStatus(String orderStatus);

  @Query("SELECT o FROM Order o WHERE o.customer = :customer ORDER BY o.createdAt DESC")
  List<Order> findOrderByCustomer(@Param("customer") Customer customer);

  @Query("SELECT o FROM Order o WHERE o.orderStatus = ?1")
  List<Order> findOrderByStatus(String orderStatus);


    @Query(value = "SELECT * FROM orders o WHERE o.customer_id = :customerId ORDER BY o.created_at DESC"
            ,nativeQuery = true)
    List<Order> findOrderByCustomerNative(@Param("customerId") Long customerId);

    @Query(value = "SELECT * FROM orders o WHERE o.order_status = ?1"
            ,nativeQuery = true)
    List<Order> findOrderByStatusNative(String orderStatus);

    @Query(value = "UPDATE Order o set o.orderStatus = ?2, o.updatedAt = CURRENT_TIMESTAMP, o.updatedBy = ?3 WHERE o.orderId = ?1")
    @Modifying
    @Transactional
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus, @Param("updatedBy") String updatedBy);
}