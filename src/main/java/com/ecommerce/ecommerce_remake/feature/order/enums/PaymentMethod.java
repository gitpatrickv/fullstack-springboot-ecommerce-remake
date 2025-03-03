package com.ecommerce.ecommerce_remake.feature.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    CASH_ON_DELIVERY("cashOnDelivery"),
    STRIPE_PAYMENT("stripePayment");

    private final String beanName;
}
