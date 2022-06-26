package com.storage.general.annotation;

import com.storage.authentication.dto.CompanyRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CompanyRegistrationDto dto = (CompanyRegistrationDto) obj;
        return dto.getPassword().equals(dto.getMatchingPassword());
    }
}