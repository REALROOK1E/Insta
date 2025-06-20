package com.zekai.framework.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String>{
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
       return phoneNumber != null && phoneNumber.matches("\\d{11}");
    }
}
