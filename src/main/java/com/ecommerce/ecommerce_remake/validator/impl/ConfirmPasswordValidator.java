package com.ecommerce.ecommerce_remake.validator.impl;

import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.validator.ConfirmPasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordValid, UserModel> {
    @Override
    public void initialize(ConfirmPasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserModel model, ConstraintValidatorContext constraintValidatorContext) {
        if (model == null || model.getPassword() == null || model.getConfirmPassword() == null) {
            return false;
        }

        return model.getPassword().equals(model.getConfirmPassword());
    }
}