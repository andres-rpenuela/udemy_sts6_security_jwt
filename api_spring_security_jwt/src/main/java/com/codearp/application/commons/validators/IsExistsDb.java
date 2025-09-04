package com.codearp.application.commons.validators;

import com.codearp.application.commons.validators.impls.GenericFieldIsExistedDb;
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
@Constraint(validatedBy = {GenericFieldIsExistedDb.class})
public @interface IsExistsDb {
    String message() default "{IsExistsDb.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldName();
}
