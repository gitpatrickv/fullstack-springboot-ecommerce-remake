package com.ecommerce.ecommerce_remake.validator.impl;

import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.validator.UniqueContactNumberValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UniqueContactNumberValidator implements ConstraintValidator<UniqueContactNumberValid, String> {

    private final StoreRepository storeRepository;
    @Override
    public void initialize(UniqueContactNumberValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        return storeRepository.findByContactNumber(number).isEmpty();
    }
}
