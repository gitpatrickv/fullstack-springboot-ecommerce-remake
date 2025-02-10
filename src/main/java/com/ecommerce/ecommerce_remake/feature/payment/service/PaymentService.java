package com.ecommerce.ecommerce_remake.feature.payment.service;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import com.stripe.exception.StripeException;

import java.util.List;

public interface PaymentService {

    PaymentResponse paymentLink(List<CartItem> cartItems, PaymentMethod paymentMethod) throws StripeException;
}
