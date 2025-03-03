package com.ecommerce.ecommerce_remake.feature.payment.service;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashOnDelivery implements PaymentService{
    @Override
    public PaymentResponse processPayment(List<CartItem> cartItems, int shippingFee) {
        return new PaymentResponse("http://localhost:5173/user/purchase/order/all");
    }
}
