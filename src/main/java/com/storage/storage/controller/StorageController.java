package com.storage.storage.controller;


import com.storage.company.domain.Company;
import com.storage.company.service.ICompanyService;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.IRestaurantService;
import com.storage.storage.domain.Storage;
import com.storage.storage.service.IStorageService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class StorageController {

    private final IStorageService _storageService;
    private final IUserService _userService;
    private final ICompanyService _companyService;
    private final IRestaurantService _restaurantService;


    public StorageController(IStorageService storageService, IUserService userService, ICompanyService companyService, IRestaurantService _restaurantService) {
        this._storageService = storageService;
        this._userService = userService;
        this._companyService = companyService;
        this._restaurantService = _restaurantService;
    }

    @GetMapping(value={"/owners/storages","/admins/storages","/workers/storages"})
    public String showStorageDashboard(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        List<Storage> storages = new ArrayList<>();
        if (user.getRoles().equals("ROLE_OWNER")) {
            Set<Restaurant> restaurants = _companyService.getCompanyByUser(user).getRestaurants();
            for (Restaurant restaurant : restaurants) {
                storages.addAll(_storageService.getAllStoragesByRestaurantId(restaurant.getId()));
            }
            model.addAttribute("restaurants", restaurants);
        } else {
            Restaurant restaurant = user.getRestaurant();
            model.addAttribute("restaurant", restaurant);
            storages.addAll(_storageService.getAllStoragesByRestaurantId(restaurant.getId()));
        }
        model.addAttribute("storages", storages);
        return "/storages/dashboard";
    }

    @GetMapping("/owners/restaurants/storages/{restaurantId}")
    public String showStoragesToCertainRestaurant(@PathVariable("restaurantId") int restaurantId, Principal principal, Model model) {

        User user = _userService.getUserByUsername(principal.getName());
        Restaurant restaurant = _restaurantService.getRestaurantById(restaurantId);
        if(!user.getRoles().equals("ROLE_OWNER") || !restaurant.getCompany().getOwner().equals(user)) {
            return "redirect:/storages";
        }
        List<Storage> storages = _storageService.getAllStoragesByRestaurantId(restaurantId);

        model.addAttribute("storages", storages);
        model.addAttribute("restaurant", restaurant);
        return "storages/dashboard";
    }

    @GetMapping(value={"/owners/storages/add/{restaurantId}","/admins/storages/add/{restaurantId}"})
    public String addStorageToRestaurant(@PathVariable("restaurantId") int restaurantId, Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        Restaurant restaurant = _restaurantService.getRestaurantById(restaurantId);
        if(user.getRoles().equals("ROLE_ADMIN") || (user.getRoles().equals("ROLE_OWNER") && restaurant.getCompany().getOwner().equals(user))) {
            Storage storage = new Storage();
            model.addAttribute("storage", storage);
            model.addAttribute("restaurant", restaurant);
            return "storages/add";
        }

        if(user.getRoles().equals("ROLE_OWNER")){
            return "redirect:/owners/storages";
        }
        else if(user.getRoles().equals("ROLE_ADMIN")){
            return "redirect:/admins/storages";
        }
        return "redirect:/";

    }

    @PostMapping(value = {"/admins/storages/add/{restaurantId}","/owners/storages/add/{restaurantId}"})
    public String addNewStorage(@ModelAttribute("storage") @Valid Storage storage,
                                @PathVariable("restaurantId") int restaurantId,
                                Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        Restaurant restaurant = _restaurantService.getRestaurantById(storage.getRestaurantId());

        if(user.getRoles().equals("ROLE_ADMIN") || (user.getRoles().equals("ROLE_OWNER") || restaurant.getCompany().getOwner().equals(user))) {
            storage.setRestaurantId(restaurantId);
            _storageService.addStorage(storage);
            if(user.getRoles().equals("ROLE_OWNER")){
                return "redirect:/owners/storages";
            }
            else if(user.getRoles().equals("ROLE_ADMIN")){
                return "redirect:/admins/storages";
            }
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/owners/storages/add/choose")
    public String addNewStorageToAChosenRestaurant(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        List<Restaurant> restaurants = _restaurantService.getAllRestaurantsByCompanyId(_companyService.getCompanyByUser(user).getId());
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("storage", new Storage());
        return "storages/add";
    }

    @PostMapping(value = {"/owners/storages/add/choose"})
    public String addNewStorage(@ModelAttribute("storage") @Valid Storage storage,
                                Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        if (storage != null) {
                if((user.getRoles().equals("ROLE_OWNER") && _restaurantService.getRestaurantById(storage.getRestaurantId()).getCompany().getOwner().equals(user))) {
                _storageService.addStorage(storage);
                if(user.getRoles().equals("ROLE_OWNER")){
                    return "redirect:/owners/storages";
                }
                else if(user.getRoles().equals("ROLE_ADMIN")){
                    return "redirect:/admins/storages";
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping(value={"/admins/storages/edit/{storageId}","/owners/storages/edit/{storageId}"})
    public String showEditStorage(@PathVariable("storageId") int storageId, Principal principal, Model model) {
        Storage storage = _storageService.getStorageById(storageId);
        if (storage != null){
            model.addAttribute("storage", storage);
            return "storages/edit";
        }
        return "redirect:/";
    }

    @GetMapping(value={"/owners/storages/delete/{storageId}", "/admins/storages/delete/{storageId}"})
    public String deleteStorage(@PathVariable("storageId") int storageId, Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        _storageService.deleteStorageById(storageId);
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/storages";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/storages";
        }
        return "redirect:/";
    }

    @PostMapping(value = {"/admins/storages/update/{storageId}","/owners/storages/update/{storageId}"})
    public String editStorage(@ModelAttribute("storage") @Valid Storage storage,
                              @PathVariable("storageId") int storageIdToUpdate,
                              Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        if (storage != null) {
                _storageService.updateStorage(storage, storageIdToUpdate);
            }
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/storages";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/storages";
        }
        return "redirect:/";    }


    // TODO : configure this if check and add to edit and delete, to see if the user can edit/delete the storage
    //  if((user.getRoles().equals("ROLE_OWNER") && (_storageService.getStorageById(storageIdToUpdate).getRestaurantId().getCompany().getOwner().equals(user)) ||
    //                    (user.getRestaurant() != null && _storageService.getStorageById(storageIdToUpdate).getRestaurant().getId() == user.getRestaurant().getId()))) {


}