package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.PaymentIntentRequestDto;
import com.eazybytes.StickyVibe.dto.PaymentIntentResponseDto;
import com.eazybytes.StickyVibe.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentIntentResponseDto> createPaymentIntent(@RequestBody PaymentIntentRequestDto paymentRequest ) {
        PaymentIntentResponseDto response = paymentServiceImpl.createPaymentIntent(paymentRequest);
        return ResponseEntity.ok(response);
    }
}

