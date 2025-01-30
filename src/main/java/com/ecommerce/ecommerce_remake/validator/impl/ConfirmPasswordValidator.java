package com.ecommerce.ecommerce_remake.validator.impl;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.validator.ConfirmPasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordValid, Object> {
    @Override
    public void initialize(ConfirmPasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserModel model = (UserModel) object;

        return model.getPassword().equals(model.getConfirmPassword());
    }
}