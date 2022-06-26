package com.storage.user.service;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.restaurant.domain.Restaurant;
import com.storage.user.domain.User;
import com.storage.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

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
        user.setPicture(null);
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

    public User updateUser(User user, Integer userToUpdateId) {

        User userToUpdate = _userRepository.findById(userToUpdateId).orElse(null);
        if(userToUpdate != null) {
            if(user.getName() != null && user.getName() != "") {
                userToUpdate.setName(user.getName());
            }
            if(user.getUsername() != null && user.getUsername() != "") {
                userToUpdate.setUsername(user.getUsername());
            }
            if(user.getEnabled() != null) {
                userToUpdate.setEnabled(user.getEnabled());
            }
            if(user.getPersonalCode() != null && user.getPersonalCode() != "") {
                userToUpdate.setPersonalCode(user.getPersonalCode());
            }
            if(user.getSurname() != null && user.getSurname() != "") {
                userToUpdate.setSurname(user.getSurname());
            }
            if(user.getEmail() != null && user.getEmail() != "") {
                userToUpdate.setEmail(user.getEmail());
            }
            if(user.getRoles() != null && user.getRoles() != "") {
                userToUpdate.setRoles(user.getRoles());
            }
            if(user.getRestaurant() != null){
                userToUpdate.setRestaurant(user.getRestaurant());
            }
            if (user.getPassword() != null && user.getPassword() != "" && user.getPassword() != userToUpdate.getPassword() ) {
                final String encryptedPassword = _passwordEncoder.encode(user.getPassword());
                userToUpdate.setPassword(encryptedPassword);
            }
            else{
                userToUpdate.setPassword(userToUpdate.getPassword());
            }

            return _userRepository.save(userToUpdate);
        }
        return null;
    }

    public User deleteUser(Integer id){
        User user = _userRepository.findById(id).orElse(null);
        if(user != null) {
            _userRepository.delete(user);
            return user;
        }
        return null;
    }

    public User updatePicture(String picture,String userToUpdateName){
        User userToUpdate = _userRepository.findByUsername(userToUpdateName).orElse(null);
        if(userToUpdate != null) {
            userToUpdate.setPicture(picture);
            return _userRepository.save(userToUpdate);
        }
        return null;
    }

    public User disableUser(Integer userId){
        User user = _userRepository.findById(userId).orElse(null);
        if(user != null) {
            user.setEnabled(false);
            return _userRepository.save(user);
        }
        return null;
    }

}
