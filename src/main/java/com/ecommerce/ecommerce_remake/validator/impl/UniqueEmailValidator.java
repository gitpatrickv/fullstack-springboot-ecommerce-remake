package com.ecommerce.ecommerce_remake.validator.impl;

import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.validator.UniqueEmailValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailValid, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueEmailValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByEmail(email).isEmpty();
    }
}