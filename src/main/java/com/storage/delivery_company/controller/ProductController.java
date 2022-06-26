package com.storage.delivery_company.controller;

import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
import com.storage.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    private final IProductService _productService;

    public ProductController(IProductService productService) {
        this._productService = productService;
    }

    @GetMapping("/products/list")
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