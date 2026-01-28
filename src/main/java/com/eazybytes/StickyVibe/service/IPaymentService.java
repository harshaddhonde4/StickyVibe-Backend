package com.eazybytes.StickyVibe.service;

import com.eazybytes.StickyVibe.dto.PaymentIntentRequestDto;
import com.eazybytes.StickyVibe.dto.PaymentIntentResponseDto;

public interface IPaymentService
{
    PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto paymentIntentRequestDto);
}
