package com.codearp.application.commons.validators.impls;

import com.codearp.application.commons.validators.IsExistsDb;
import com.codearp.application.products.services.ProductService;
import com.codearp.application.users.services.PersonService;
import com.codearp.application.users.services.UserAccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenericFieldIsExistedDb implements ConstraintValidator<IsExistsDb, String> {
    private String fieldName;

    private final ProductService productService;
    private final UserAccountService userAccountService;
    private final PersonService personService;

    @Override
    public void initialize(IsExistsDb constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.fieldName =  constraintAnnotation.fieldName();
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String cleanedValue = StringUtils.stripToNull(value);
        if ( cleanedValue == null ) {
            return false;
        }

        return switch (fieldName) {
            case "sku" -> !productService.existsBySkuIgnoreCase(cleanedValue);
            case "userName" -> !userAccountService.isExistedById(cleanedValue);
            case "email" -> !personService.existsByEmailIgnoreCase(cleanedValue);
            default -> false; // si no reconoces el campo, lo invalidas
        };
    }

}
