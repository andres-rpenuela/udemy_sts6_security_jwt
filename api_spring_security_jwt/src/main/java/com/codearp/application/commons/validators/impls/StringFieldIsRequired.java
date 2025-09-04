package com.codearp.application.commons.validators.impls;

import com.codearp.application.commons.validators.IsRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringFieldIsRequired implements ConstraintValidator<IsRequired, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  value != null && !value.isEmpty() && !value.isBlank();
    }
}
