package com.storage.product.controller;

import com.storage.product.database.domain.Product;
import com.storage.product.database.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index() {
        return "redirect:/products/list";
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<Product> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "products/list";
    }
//    @GetMapping("/add")
//    public String listAll() {
//        List<Product> productList = productService.getAll();
//        return "products/list";
//    }
}