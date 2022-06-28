package com.storage.restaurant.controller;


import com.storage.company.domain.Company;
import com.storage.company.service.ICompanyService;
import com.storage.logger.database.service.IDatabaseLoggerService;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.IRestaurantService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
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

    private final IRestaurantService _restaurantService;
    private final ICompanyService _companyService;
    private final IUserService _userService;
    private final IDatabaseLoggerService logger;

    public RestaurantController(IRestaurantService _restaurantService, ICompanyService companyService, IUserService userService, IDatabaseLoggerService logger) {
        this._restaurantService = _restaurantService;
        this._companyService = companyService;
        this._userService = userService;
        this.logger = logger;
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
            logger.info("Added new restaurant: " + restaurant.getName(), principal);
            return "redirect:/owners/restaurants";
        }
        else{
            bindingResult.rejectValue("company", "company.not.found");
            logger.error("Company owned by user not found", principal);
            return "owners/restaurants/add";
        }
    }

    @GetMapping(value={"/owners/restaurants/edit/{restaurantId}"})
    public String showEditRestaurantView(@PathVariable("restaurantId") int restaurantId, Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        Company company = _companyService.getCompanyByUser(user);
        Restaurant restaurant = _restaurantService.getRestaurantById(restaurantId);
        if(restaurant.getCompany().getId() != company.getId()) {
            return "redirect:/owners/restaurants";
        }
        model.addAttribute("restaurant", restaurant);
        return "owners/restaurants/edit";
    }

    @PostMapping(value={"/owners/restaurants/update/{restaurantId}"})
    public String editRestaurant(@PathVariable("restaurantId") int restaurantIdToUpdate,
                                 @ModelAttribute("restaurant") @Valid Restaurant restaurant, Principal principal, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "owners/restaurants/edit";
        }
        _restaurantService.updateRestaurant(restaurant, restaurantIdToUpdate);
        logger.info("Updated restaurant with id: " + restaurant.getId(), principal, restaurant.getId());
        return "redirect:/owners/restaurants";
    }

    @GetMapping(value={"/owners/restaurants/delete/{restaurantId}"})
    public String deleteRestaurant(@PathVariable("restaurantId") int restaurantId, Model model, Principal principal){
        _restaurantService.deleteRestaurant(restaurantId);
        logger.info("Deleted restaurant with id: " + restaurantId, principal, restaurantId);
        return "redirect:/owners/restaurants";
    }
    
}
