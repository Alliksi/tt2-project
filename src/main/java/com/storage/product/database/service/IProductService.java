package com.storage.product.database.service;

import com.storage.product.database.domain.Product;
import org.springframework.ui.Model;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
}
