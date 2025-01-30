package com.ecommerce.ecommerce_remake.validator.impl;

import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.validator.UniqueStoreValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueStoreValidator implements ConstraintValidator<UniqueStoreValid, String> {

    private final StoreRepository storeRepository;

    @Override
    public void initialize(UniqueStoreValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String storeName, ConstraintValidatorContext constraintValidatorContext) {
        return storeRepository.findByStoreNameIgnoreCase(storeName).isEmpty();
    }
}
