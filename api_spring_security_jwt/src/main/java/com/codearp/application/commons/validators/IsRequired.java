package com.codearp.application.commons.validators;

import com.codearp.application.commons.validators.impls.StringFieldIsRequired;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD } )
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {StringFieldIsRequired.class})
public @interface IsRequired {
    String message() default "{IsRequired.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
