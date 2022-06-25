package com.storage.auth.annotations.validators;

import com.storage.auth.annotations.PasswordMatches;
import com.storage.auth.dto.CompanyRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CompanyRegistrationDto dto = (CompanyRegistrationDto) obj;
        return dto.getPassword().equals(dto.getMatchingPassword());
    }
}