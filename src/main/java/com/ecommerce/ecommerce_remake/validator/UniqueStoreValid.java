package com.ecommerce.ecommerce_remake.validator;

import com.ecommerce.ecommerce_remake.validator.impl.UniqueStoreValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueStoreValidator.class)
public @interface UniqueStoreValid {

    String message() default "{store.name.exist}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
