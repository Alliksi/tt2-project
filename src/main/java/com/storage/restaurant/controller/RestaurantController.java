package com.storage.restaurant.controller;


import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.company.service.CompanyService;
import com.storage.company.service.ICompanyService;
import com.storage.product.domain.Product;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.IRestaurantService;
import com.storage.restaurant.service.RestaurantService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import com.storage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class RestaurantController {

    final IRestaurantService _restaurantService;
    final ICompanyService _companyService;
    final IUserService _userService;

    public RestaurantController(IRestaurantService _restaurantService, ICompanyService companyService, IUserService userService) {
        this._restaurantService = _restaurantService;
        this._companyService = companyService;
        this._userService = userService;
    }

    @GetMapping(value={"/owners/restaurants"})
    public String showRestaurantDashboard(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(user);
        if(company != null){
            List<Restaurant> restaurantList = _restaurantService.getAllRestaurantsByCompanyId(company.getId());
            model.addAttribute("restaurants", restaurantList);
            return "owners/restaurants/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping(value={"/owners/restaurants/add"})
    public String showAddRestaurantView(Model model) {
        Restaurant restaurant = new Restaurant();
        model.addAttribute("restaurant", restaurant);
        return "owners/restaurants/add";
    }

    @PostMapping(value={"/owners/restaurants/add"})
    public String addNewRestaurant(Principal principal, @ModelAttribute("restaurant") @Valid Restaurant restaurant, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "owners/restaurants/add";
        }
        User user = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(user);
        if(company != null){
            restaurant.setCompany(company);
            _restaurantService.addRestaurant(restaurant);
            return "redirect:/owners/restaurants";
        }
        else{
            bindingResult.rejectValue("company", "company.not.found");
            return "owners/restaurants/add";
        }
    }

    @GetMapping(value={"/owners/restaurants/edit/{restaurantId}"})
    public String showEditRestaurantView(@PathVariable("restaurantId") int restaurantId, Model model) {
        Restaurant restaurant = _restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "owners/restaurants/edit";
    }

    @PostMapping(value={"/owners/restaurants/update/{restaurantId}"})
    public String editRestaurant(@PathVariable("restaurantId") int restaurantIdToUpdate,
                                 @ModelAttribute("restaurant") @Valid Restaurant restaurant, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "owners/restaurants/edit";
        }
        _restaurantService.updateRestaurant(restaurant, restaurantIdToUpdate);
        return "redirect:/owners/restaurants";
    }

    @GetMapping(value={"/owners/restaurants/delete/{restaurantId}"})
    public String deleteRestaurant(@PathVariable("restaurantId") int restaurantId, Model model){
        _restaurantService.deleteRestaurant(restaurantId);
        return "redirect:/owners/restaurants";
    }
    
}
