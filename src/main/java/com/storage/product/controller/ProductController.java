package com.storage.product.controller;

import com.storage.company.service.ICompanyService;
import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
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
public class ProductController {

    private final IProductService _productService;
    private final IShelfService _shelfService;
    private final IUserService _userService;
    private final ICompanyService _companyService;
    private final IStorageService _storageService;
    private final IRestaurantService _restaurantService;

    public ProductController(IProductService productService, IShelfService shelfService, IUserService userService, ICompanyService companyService, IStorageService storageService, IRestaurantService _restaurantService) {
        this._productService = productService;
        this._shelfService = shelfService;
        this._userService = userService;
        this._companyService = companyService;
        this._storageService = storageService;
        this._restaurantService = _restaurantService;
    }


    private void getProductsFromStorages(List<Product> products, List<Storage> storages) {
        for (Storage storage : storages) {
            List<Shelf> shelves = new ArrayList<>();
            shelves.addAll(_shelfService.getAllByStorageId(storage.getStorageId()));
            for(Shelf shelf: shelves){
                products.addAll(_productService.getAllByShelfId(shelf.getShelfId()));
            }
        }
    }

    @GetMapping(value={"/admins/products","/owners/products","/workers/products"})
    public String showProductsDashboard(Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        List<Product> products = new ArrayList<>();

        if (user.getRoles().equals("ROLE_OWNER")) {
            Set<Restaurant> restaurants = _companyService.getCompanyByUser(user).getRestaurants();
            for (Restaurant restaurant : restaurants) {
                List<Storage> storages = _storageService.getAllStoragesByRestaurantId(restaurant.getId());
                getProductsFromStorages(products, storages);
            }
        } else {
            List<Storage> storages = _storageService.getAllStoragesByRestaurantId(user.getRestaurant().getId());
            getProductsFromStorages(products, storages);
        }
        model.addAttribute("products", products);
        return "/products/dashboard";
    }


    @GetMapping(value={"/workers/products/add/{shelfId}", "/owners/products/add/{shelfId}","/admins/products/add/{shelfId}"})
    public String addNewProductView(Principal principal, Model model,  @PathVariable("shelfId") int shelfId) {
        Shelf shelf = _shelfService.getShelfById(shelfId);
        model.addAttribute("shelf", shelf);
        model.addAttribute("product", new Product());
        return "/products/add";
    }

    @PostMapping(value={"/workers/products/add/{shelfId}","/owners/products/add/{shelfId}","/admins/products/add/{shelfId}"})
    public String addNewProduct(Principal principal, Model model, @PathVariable("shelfId") int shelfId, @Valid @ModelAttribute("product") Product product) {
        User user = _userService.getUserByUsername(principal.getName());
        Shelf shelf = _shelfService.getShelfById(shelfId);
        product.setStorageType(shelf.getStorageType());
        product.setShelfId(shelfId);
        _productService.addProduct(product);
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/products";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/products";
        }
        else if(user.getRoles().equals("ROLE_WORKER")){
            return "redirect:/workers/products";
        }
        return "redirect:/";
    }


    @GetMapping(value={"/workers/products/edit/{productId}","/admins/products/edit/{productId}","/owners/products/edit/{productId}"})
    public String showEditProduct(@PathVariable("productId") int productId, Principal principal, Model model) {
        Product product = _productService.getProductById(productId);
        if (product != null){
            model.addAttribute("product", product);
            return "products/edit";
        }
        return "redirect:/";
    }

    @PostMapping(value = {"/workers/products/update/{productId}","/admins/products/update/{productId}","/owners/products/update/{productId}"})
    public String editProduct(@ModelAttribute("product") @Valid Product product,
                              @PathVariable("productId") int productIdToUpdate,
                              Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        if (product != null) {
            _productService.updateProduct(product, productIdToUpdate);
        }
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/products";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/products";
        }
        else if(user.getRoles().equals("ROLE_WORKER")){
            return "redirect:/workers/products";
        }
        return "redirect:/";
    }

    @GetMapping(value={"/owners/products/delete/{productId}","/workers/products/delete/{productId}", "/admins/products/delete/{productId}"})
    public String deleteProduct(@PathVariable("productId") int productId, Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        _productService.deleteProductById(productId);
        if (user.getRoles().equals("ROLE_OWNER")) {
            return "redirect:/owners/products";
        } else if (user.getRoles().equals("ROLE_ADMIN")) {
            return "redirect:/admins/products";
        }
        else if(user.getRoles().equals("ROLE_WORKER")){
            return "redirect:/workers/products";
        }
        return "redirect:/";
    }

}