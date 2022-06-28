package com.storage.product.service;

import com.storage.product.domain.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllByShelfId(Integer shelfId);

    Product deleteProductById(Integer productId);

    Product updateProduct(Product product, Integer productToUpdateId);

    Product addProduct(Product product);

    Product getProductById(Integer productId);
}
