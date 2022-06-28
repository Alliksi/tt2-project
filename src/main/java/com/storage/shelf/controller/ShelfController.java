package com.storage.shelf.controller;

import com.storage.company.service.ICompanyService;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.IRestaurantService;
import com.storage.shelf.domain.Shelf;
import com.storage.shelf.service.IShelfService;
import com.storage.storage.domain.Storage;
import com.storage.storage.service.IStorageService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ShelfController {

    private final IShelfService _shelfService;
    private final IUserService _userService;
    private final ICompanyService _companyService;
    private final IStorageService _storageService;
    private final IRestaurantService _restaurantService;

    public ShelfController(IShelfService shelfService, IUserService userService, ICompanyService companyService, IStorageService storageService, IRestaurantService _restaurantService) {
        this._shelfService = shelfService;
        this._userService = userService;
        this._companyService = companyService;
        this._storageService = storageService;
        this._restaurantService = _restaurantService;
    }

    @GetMapping(value={"/admins/shelves","/owners/shelves","/workers/shelves"})
    public String showShelvesDashboard(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        List<Shelf> shelves = new ArrayList<>();

        if (user.getRoles().equals("ROLE_OWNER")) {
            Set<Restaurant> restaurants = _companyService.getCompanyByUser(user).getRestaurants();
            for (Restaurant restaurant : restaurants) {
                List<Storage> storages = _storageService.getAllStoragesByRestaurantId(restaurant.getId());
                for(Storage storage : storages) {
                    shelves.addAll(_shelfService.getAllByStorageId(storage.getStorageId()));
                }
            }
        } else {
            List<Storage> storages = _storageService.getAllStoragesByRestaurantId(user.getRestaurant().getId());
            for (Storage storage : storages) {
                shelves.addAll(_shelfService.getAllByStorageId(storage.getStorageId()));
            }
        }
        model.addAttribute("shelves", shelves);
        return "/shelves/dashboard";
    }


    @GetMapping(value={"/owners/shelves/add/choose","/admins/shelves/add/choose"})
    public String addNewShelf(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        List<Storage> storages = new ArrayList<>();
        if (user.getRoles().equals("ROLE_OWNER")) {
            Set<Restaurant> restaurants = _companyService.getCompanyByUser(user).getRestaurants();
            for (Restaurant restaurant : restaurants) {
                storages.addAll(_storageService.getAllStoragesByRestaurantId(restaurant.getId()));
            }
        } else {
            storages.addAll(_storageService.getAllStoragesByRestaurantId(user.getRestaurant().getId()));
        }
        model.addAttribute("shelf", new Shelf());
        model.addAttribute("storages", storages);
        return "/shelves/add";
    }

    @PostMapping(value = {"/owners/shelves/add/choose","/admins/shelves/add/choose"})
    public String addNewStorage(@ModelAttribute("shelf") @Valid Shelf shelf,
                                Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        if (shelf != null) {
                _shelfService.addShelf(shelf);
                if(user.getRoles().equals("ROLE_OWNER")){
                    return "redirect:/owners/shelves";
                }
                else if(user.getRoles().equals("ROLE_ADMIN")){
                    return "redirect:/admins/shelves";
                }
        }
        return "redirect:/";
    }

    @GetMapping(value={"/admins/shelves/edit/{shelfId}","/owners/shelves/edit/{shelfId}"})
    public String showEditStorage(@PathVariable("shelfId") int shelfId, Principal principal, Model model) {
        Shelf shelf = _shelfService.getShelfById(shelfId);
        if (shelf != null){
            model.addAttribute("shelf", shelf);
            return "shelves/edit";
        }
        return "redirect:/";
    }

    @GetMapping(value={"/owners/shelves/delete/{shelfId}", "/admins/shelves/delete/{shelfId}"})
    public String deleteStorage(@PathVariable("shelfId") int shelfId, Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        _shelfService.deleteShelfById(shelfId);
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/shelves";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/shelves";
        }
        return "redirect:/";
    }

    @PostMapping(value = {"/admins/shelves/update/{shelfId}","/owners/shelves/update/{shelfId}"})
    public String editStorage(@ModelAttribute("shelf") @Valid Shelf shelf,
                              @PathVariable("shelfId") int shelfIdToUpdate,
                              Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        if (shelf != null) {
            _shelfService.updateShelf(shelf, shelfIdToUpdate);
        }
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/shelves";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/shelves";
        }
        return "redirect:/";
    }
}