package com.storage.user.controller;


import com.storage.company.domain.Company;
import com.storage.company.service.ICompanyService;
import com.storage.general.exception.UserAlreadyExistsException;
import com.storage.general.utility.PasswordGenerator;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.IRestaurantService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    final IUserService _userService;
    final IRestaurantService _restaurantService;
    final ICompanyService _companyService;

    public UserController(IUserService _userService, IRestaurantService restaurantService, ICompanyService companyService) {
        this._userService = _userService;
        this._restaurantService = restaurantService;
        this._companyService = companyService;
    }

    @GetMapping("/owners/employees")
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

    @GetMapping("/owners/employees/add")
    public String showOwnerAddEmployeeView(Principal principal, Model model) {
        User admin = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(admin);
        List<Restaurant> restaurants = _restaurantService.getAllRestaurantsByCompanyId(company.getId());
        model.addAttribute("restaurants", restaurants);
        User user = new User();
        model.addAttribute("employee", user);
        return "owners/employees/add";
    }

    @GetMapping("/owners/employees/edit/{employeeId}")
    public String showEditOwnerUserView(@PathVariable("employeeId") int employeeIdToUpdate, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "owners/employees/edit";
        }

        User employeeToUpdate = _userService.getUserById(employeeIdToUpdate);

        if (employeeToUpdate != null) {
            model.addAttribute("employee", employeeToUpdate);
            return "owners/employees/edit";
        }

        return "owners/employees/dashboard";
    }

    @PostMapping("/owners/employees/add")
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
        return "owners/employees/added_info";
    }

//    @GetMapping("owners/employees/added_info")
//    public String showOwnerAddedEmployeeInfo(Model model) {
//
//        return "owners/employees/added_info";
//    }
}
