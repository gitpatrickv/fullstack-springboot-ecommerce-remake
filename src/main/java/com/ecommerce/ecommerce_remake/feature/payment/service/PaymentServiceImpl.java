package com.ecommerce.ecommerce_remake.feature.payment.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse paymentLink(Long totalAmount, PaymentMethod paymentMethod) throws StripeException {

        if(paymentMethod.equals(PaymentMethod.CASH_ON_DELIVERY)){
            return new PaymentResponse("http://localhost:5173/user/purchase/order/all");
        }

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
                                .setUnitAmount(totalAmount*100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Ecommerce").build())
                                .build())
                        .build())
                .build();

        Session session =  Session.create(params);

        return new PaymentResponse(session.getUrl());

    }
}
