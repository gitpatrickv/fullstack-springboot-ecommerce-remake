package com.ecommerce.ecommerce_remake.validator;

import com.ecommerce.ecommerce_remake.validator.impl.UniqueContactNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueContactNumberValidator.class)
public @interface UniqueContactNumberValid {

    String message() default "{contact.number.exist}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
