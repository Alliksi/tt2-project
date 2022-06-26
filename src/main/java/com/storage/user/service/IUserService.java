package com.storage.user.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.user.domain.User;

public interface IUserService {
    User registerNewUser(CompanyRegistrationDto companyRegistrationDto, String role) throws UserAlreadyExistsException;
    Boolean checkIfUserExistsByPersonalCode(String personalCode);
    Boolean checkIfUserExistsByUsername(String username);
}
