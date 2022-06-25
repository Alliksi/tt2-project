package com.storage.company.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.company.repository.CompanyRepository;
import com.storage.general.exception.CompanyAlreadyExistsException;
import com.storage.user.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompanyService implements ICompanyService {

    final
    CompanyRepository _companyRepository;

    public CompanyService(CompanyRepository _companyRepository) {
        this._companyRepository = _companyRepository;
    }

    public Company registerNewCompany(CompanyRegistrationDto companyRegistrationDto, User owner) throws CompanyAlreadyExistsException {
        if (checkIfCompanyExistsByRegistrationNumber(companyRegistrationDto.getRegistrationNumber())) {
            throw new CompanyAlreadyExistsException("Company with a registration number: " +
                    companyRegistrationDto.getRegistrationNumber() + " already exists");
        }
        Company company = new Company();
        company.setName(companyRegistrationDto.getCompanyName());
        company.setRegistrationNumber(companyRegistrationDto.getRegistrationNumber());
        company.setOwner(owner);
        return _companyRepository.save(company);
    }
    public Boolean checkIfCompanyExistsByRegistrationNumber(String registrationNumber) {
        return _companyRepository.findByRegistrationNumber(registrationNumber).isPresent();
    }

}
