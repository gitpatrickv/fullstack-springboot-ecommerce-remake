package com.ecommerce.ecommerce_remake.feature.payment.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentResponse paymentLink(Long totalAmount, PaymentMethod paymentMethod) throws StripeException;
}
