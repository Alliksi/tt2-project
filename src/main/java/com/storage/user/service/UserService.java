package com.storage.user.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.user.domain.User;
import com.storage.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{

    private final UserRepository _userRepository;

    public UserService(UserRepository _userRepository) {
        this._userRepository = _userRepository;
    }

    @Override
    public User registerNewUser(CompanyRegistrationDto companyRegistrationDto, String role) throws UserAlreadyExistsException{
        if (checkIfUserExistsByPersonalCode(companyRegistrationDto.getPersonalCode())) {
            throw new UserAlreadyExistsException("An administrator with personal code "
                        + companyRegistrationDto.getPersonalCode() +" already exists.");
        }

        User user = new User();
        user.setName(companyRegistrationDto.getPersonName());
        user.setSurname(companyRegistrationDto.getPersonSurname());
        user.setUsername(companyRegistrationDto.getUsername());

        user.setPassword(companyRegistrationDto.getPassword());
        user.setEmail(companyRegistrationDto.getEmail());
        user.setEnabled(true);
        user.setRole(role);
        return _userRepository.save(user);

    }

    public Boolean checkIfUserExistsByPersonalCode(String personalCode) {
        return _userRepository.findByPersonalCode(personalCode).isPresent();
    }
}
