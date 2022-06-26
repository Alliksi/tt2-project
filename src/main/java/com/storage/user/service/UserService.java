package com.storage.user.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.user.domain.User;
import com.storage.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        user.setPassword(encryptedPassword);
        user.setEmail(companyRegistrationDto.getEmail());
        user.setEnabled(true);
        user.setRoles(roles);
        return _userRepository.save(user);
    }

    @Override
    public User registerNewUser(User user) throws UserAlreadyExistsException{
        if (checkIfUserExistsByPersonalCode(user.getPersonalCode())) {
            throw new UserAlreadyExistsException("An employee with personal code "
                    + user.getPersonalCode() +" already exists in our database.");
        }
        if (checkIfUserExistsByEmail(user.getPersonalCode())) {
            throw new UserAlreadyExistsException("An employee with email "
                    + user.getEmail() +" already exists in our database.");
        }

        final String encryptedPassword = _passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
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

    public User getUserByUsername(String username) {
        return _userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsersByRestaurantId(Integer restaurantId) {
        return _userRepository.findAllByRestaurantId(restaurantId);
    }

    public User getUserById(Integer id){
        return _userRepository.findById(id).orElse(null);
    }
}
