package com.storage.company.repository;

import com.storage.company.domain.Company;
import com.storage.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByRegistrationNumber(String registrationNumber);
    Optional<Company> findByOwner(User owner);
}