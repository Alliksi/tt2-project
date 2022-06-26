package com.storage.user.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.user.domain.User;

import java.util.List;

public interface IUserService {
    User registerNewUser(CompanyRegistrationDto companyRegistrationDto, String role) throws UserAlreadyExistsException;
    User registerNewUser(User user) throws UserAlreadyExistsException;
    Boolean checkIfUserExistsByPersonalCode(String personalCode);
    Boolean checkIfUserExistsByUsername(String username);
    User getUserByUsername(String username);
    List<User> getAllUsersByRestaurantId(Integer restaurantId);
    User getUserById(Integer id);
    User updateUser(User user, Integer userToUpdateId);
    User deleteUser(Integer id);
    User updatePicture(String picture, String userToUpdateName);
    User disableUser(Integer userId);
}
