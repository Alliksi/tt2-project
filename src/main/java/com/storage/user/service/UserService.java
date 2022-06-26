package com.storage.user.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.user.domain.User;
import com.storage.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{

    private final UserRepository _userRepository;
    private PasswordEncoder _passwordEncoder;

    public UserService(UserRepository _userRepository, PasswordEncoder passwordEncoder) {
        this._userRepository = _userRepository;
        this._passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUser(CompanyRegistrationDto companyRegistrationDto, String roles) throws UserAlreadyExistsException{
        if (checkIfUserExistsByPersonalCode(companyRegistrationDto.getPersonalCode())) {
            throw new UserAlreadyExistsException("An administrator with personal code "
                        + companyRegistrationDto.getPersonalCode() +" already exists.");
        }
        if (checkIfUserExistsByEmail(companyRegistrationDto.getPersonalCode())) {
            throw new UserAlreadyExistsException("An administrator with email "
                    + companyRegistrationDto.getEmail() +" already exists.");
        }

        User user = new User();
        user.setName(companyRegistrationDto.getPersonName());
        user.setSurname(companyRegistrationDto.getPersonSurname());
        user.setUsername(companyRegistrationDto.getUsername());
        user.setPersonalCode(companyRegistrationDto.getPersonalCode());
        final String encryptedPassword = _passwordEncoder.encode(companyRegistrationDto.getPassword());
        System.out.println(encryptedPassword);
        user.setPassword(encryptedPassword);
        user.setEmail(companyRegistrationDto.getEmail());
        user.setEnabled(true);
        user.setRoles(roles);
        return _userRepository.save(user);
    }

    public Boolean checkIfUserExistsByPersonalCode(String personalCode) {
        return _userRepository.findByPersonalCode(personalCode).isPresent();
    }

    public Boolean checkIfUserExistsByUsername(String username) {
        return _userRepository.findByUsername(username).isPresent();
    }

    public Boolean checkIfUserExistsByEmail(String email){
        return _userRepository.findByEmail(email).isPresent();
    }
}
