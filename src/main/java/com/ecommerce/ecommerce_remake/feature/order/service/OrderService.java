package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderRequest;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.stripe.exception.StripeException;

public interface OrderService {

    PaymentResponse placeOrder(OrderRequest request) throws StripeException;
}
