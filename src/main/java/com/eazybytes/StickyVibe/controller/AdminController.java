package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.constants.ApplicationConstants;
import com.eazybytes.StickyVibe.dto.ContactResponseDto;
import com.eazybytes.StickyVibe.dto.OrderResponseDto;
import com.eazybytes.StickyVibe.dto.ResponseDto;
import com.eazybytes.StickyVibe.entity.Order;
import com.eazybytes.StickyVibe.service.IContactService;
import com.eazybytes.StickyVibe.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final IOrderService orderService;
    private final IContactService contactService;

    public AdminController(IOrderService orderService, IContactService contactService) {
        this.orderService = orderService;
        this.contactService = contactService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> loadAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllPendingOrders());
    }

    @PatchMapping("orders/{orderId}/confirm")
    public ResponseEntity<ResponseDto> confirmOrder(@PathVariable Long orderId) {
        Order confirmedOrder = orderService.updateOrderStatus(orderId, ApplicationConstants.ORDER_STATUS_CONFIRMED);
        return ResponseEntity.ok(new ResponseDto("200", "Order #" + confirmedOrder.getOrderId() + "has been confirmed successfully"));
    }

    @PatchMapping("orders/{orderId}/cancel")
    public ResponseEntity<ResponseDto> cancelOrder(@PathVariable Long orderId) {
        Order cancelledOrder = orderService.updateOrderStatus(orderId, ApplicationConstants.ORDER_STATUS_CANCELLED);
        return ResponseEntity.ok(new ResponseDto("200", "Order #" + cancelledOrder.getOrderId() + "has been cancelled successfully"));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ContactResponseDto>> loadAllContacts() {
        return ResponseEntity.ok().body(contactService.getAllOpenMessages());
    }

    @PatchMapping("/messages/{messageId}/close")
    public ResponseEntity<ResponseDto> closeMessage(@PathVariable Long messageId) {
        contactService.updateMessageStatus(messageId, ApplicationConstants.CLOSED_MESSAGE);
        return ResponseEntity.ok(new ResponseDto("200", "Message #" + messageId + "has been closed successfully"));
    }
}

