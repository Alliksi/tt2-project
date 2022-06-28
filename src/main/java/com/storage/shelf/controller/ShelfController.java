package com.storage.shelf.controller;

import com.storage.company.service.ICompanyService;
import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
import com.storage.restaurant.domain.Restaurant;
import com.storage.shelf.domain.Shelf;
import com.storage.shelf.service.IShelfService;
import com.storage.storage.domain.Storage;
import com.storage.storage.service.IStorageService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    public ShelfController(IShelfService shelfService, IUserService userService, ICompanyService companyService, IStorageService storageService) {
        this._shelfService = shelfService;
        this._userService = userService;
        this._companyService = companyService;
        this._storageService = storageService;
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
//     User user = _userService.getUserByUsername(principal.getName());
//        List<Storage> storages = new ArrayList<>();
//        if (user.getRoles().equals("ROLE_OWNER")) {
//            Set<Restaurant> restaurants = _companyService.getCompanyByUser(user).getRestaurants();
//            for (Restaurant restaurant : restaurants) {
//                storages.addAll(_storageService.getAllStoragesByRestaurantId(restaurant.getId()));
//            }
//            model.addAttribute("restaurants", restaurants);
//        } else {
//            Restaurant restaurant = user.getRestaurant();
//            model.addAttribute("restaurant", restaurant);
//            storages.addAll(_storageService.getAllStoragesByRestaurantId(restaurant.getId()));
//        }
//        model.addAttribute("storages", storages);
//        return "/storages/dashboard";
}