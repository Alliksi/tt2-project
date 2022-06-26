package com.storage.company.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.general.exception.CompanyAlreadyExistsException;
import com.storage.user.domain.User;

public interface ICompanyService {
    Company registerNewCompany(CompanyRegistrationDto companyRegistrationDto, User owner) throws CompanyAlreadyExistsException;
    Boolean checkIfCompanyExistsByRegistrationNumber(String registrationNumber);
    Company getCompanyByUser(User user);
}
