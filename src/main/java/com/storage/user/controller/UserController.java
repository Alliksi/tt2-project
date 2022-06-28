package com.storage.user.controller;


import com.storage.company.domain.Company;
import com.storage.company.service.ICompanyService;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.general.service.EmailSenderService;
import com.storage.general.utility.PasswordGenerator;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.IRestaurantService;
import com.storage.user.domain.User;
import com.storage.user.dto.NewPasswordDto;
import com.storage.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class UserController {

    private final IUserService _userService;
    private final IRestaurantService _restaurantService;
    private final ICompanyService _companyService;
    private final EmailSenderService _emailSenderService;
    public UserController(IUserService _userService, IRestaurantService restaurantService, ICompanyService companyService, EmailSenderService _emailSenderService) {
        this._userService = _userService;
        this._restaurantService = restaurantService;
        this._companyService = companyService;
        this._emailSenderService = _emailSenderService;
    }

    @GetMapping("/employees")
    public String showOwnerEmployeeDashboard(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(user);
        List<Restaurant> restaurants = _restaurantService.getAllRestaurantsByCompanyId(company.getId());
        List<User> employees = new ArrayList<>();
        if (restaurants != null) {
            for (int i = 0; i < restaurants.size(); i++) {
                List<User> restaurantEmployees = _userService.getAllUsersByRestaurantId(restaurants.get(i).getId());
                if (restaurantEmployees != null) {
                    for (int j = 0; j < restaurantEmployees.size(); j++) {
                        employees.add(restaurantEmployees.get(j));
                    }
                }
            }
        }
        model.addAttribute("employees", employees);
        return "owners/employees/dashboard";
    }

    @GetMapping("/employees/add")
    public String showOwnerAddEmployeeView(Principal principal, Model model) {
        User admin = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(admin);
        List<Restaurant> restaurants = _restaurantService.getAllRestaurantsByCompanyId(company.getId());
        model.addAttribute("restaurants", restaurants);
        User user = new User();
        model.addAttribute("employee", user);
        return "owners/employees/add";
    }

    @GetMapping("/employees/edit/{employeeId}")
    public String showEditOwnerUserView(@PathVariable("employeeId") int employeeIdToUpdate, Principal principal, Model model) {
        User admin = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(admin);
        List<Restaurant> restaurants = _restaurantService.getAllRestaurantsByCompanyId(company.getId());
        User employeeToUpdate = _userService.getUserById(employeeIdToUpdate);
        if(employeeToUpdate.getRestaurant().getCompany().getId() != company.getId()){
            return "redirect:/owners/employees";
        }
        NewPasswordDto newPassword = new NewPasswordDto();
        model.addAttribute("newPassword", newPassword);
        if(restaurants != null) {
            model.addAttribute("restaurants", restaurants);
        }
        if (employeeToUpdate != null) {
            model.addAttribute("employee", employeeToUpdate);
            return "owners/employees/edit";
        }

        return "owners/employees/dashboard";
    }

    @PostMapping("/employees/add")
    public String addEmployeeOwnerPermission(@Valid @ModelAttribute("employee") User employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "owners/employees/add";
        }

        employee.setEnabled(true);
        String password = new PasswordGenerator().generatePassword();
        employee.setPassword(password);
        try {
            _userService.registerNewUser(employee);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "owners/employees/added_info";
        }
        model.addAttribute("generatedPassword", password);
        model.addAttribute("username", employee.getUsername());
        _emailSenderService.sendMail(employee.getEmail(), "Welcome to ProductMe", "Welcome to ProductMe, your username is: " + employee.getUsername() + " and password is: " + password);
        return "owners/employees/added_info";
    }

    @PostMapping(value={"/employees/update/{employeeId}"})
    public String editEmployee(@PathVariable("employeeId") int employeeToUpdate,
                               @ModelAttribute("employee") @Valid User user,
                               @ModelAttribute("newPassword") @Valid NewPasswordDto newPassword, BindingResult bindingResult,
                               Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "owners/employees/edit";
        }

        if(newPassword.getPassword() != null && !newPassword.getPassword().equals("")){
            user.setPassword(newPassword.getPassword());
        }
        _userService.updateUser(user, employeeToUpdate);
        return "redirect:/owners/employees";
    }

    @GetMapping(value={"/employees/disable/{employeeId}"})
    public String disableEmployee(@PathVariable("employeeId") int employeeToDisable, Model model){
        _userService.disableUser(employeeToDisable);
        return "redirect:/owners/employees";
    }
}
