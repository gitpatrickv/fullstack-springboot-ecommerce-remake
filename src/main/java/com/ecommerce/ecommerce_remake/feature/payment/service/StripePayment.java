package com.ecommerce.ecommerce_remake.feature.payment.service;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.ecommerce.ecommerce_remake.feature.cart.service.CartServiceImpl.calculateTotalAmount;
@Service
@Transactional
@Slf4j
public class StripePayment implements PaymentService{

    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Override
    public PaymentResponse processPayment(List<CartItem> cartItems, int shippingFee) throws StripeException {

        BigDecimal cartTotal = calculateTotalAmount(cartItems);
        BigDecimal totalAmount = cartTotal.add(BigDecimal.valueOf(shippingFee));
        log.info("Stripe Payment - Order Total Amount {}", totalAmount);
        Stripe.apiKey=stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/user/purchase/order/to-ship")
                .setCancelUrl("http://localhost:5173/cart")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("php")
                                .setUnitAmount(totalAmount.longValue() * 100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Ecommerce").build())
                                .build())
                        .build())
                .build();

        Session session =  Session.create(params);

        return new PaymentResponse(session.getUrl());

    }
}
