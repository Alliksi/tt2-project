package com.storage.working_time.controller;

import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WorkingTimeController {

    private final IProductService _productService;

    public WorkingTimeController(IProductService productService) {
        this._productService = productService;
    }

    //@GetMapping("/products/list")
    public String listAll(Model model) {
        List<Product> productList = _productService.getAll();
        model.addAttribute("products", productList);
        return "/products/list";
    }
//    @GetMapping("/add")
//    public String listAll() {
//        List<Product> productList = productService.getAll();
//        return "products/list";
//    }
}