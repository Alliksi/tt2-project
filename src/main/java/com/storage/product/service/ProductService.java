package com.storage.product.service;

import com.storage.product.domain.Product;
import com.storage.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository _productRepository;

    public ProductService(ProductRepository productRepository) {
        this._productRepository = productRepository;
    }

    public List<Product> getAllByShelfId(Integer shelfId){
        return _productRepository.findAllByShelfId(shelfId);
    }

    public Product deleteProductById(Integer productId){
        Product product = _productRepository.findById(productId).orElse(null);
        if (product != null) {
            _productRepository.deleteById(productId);
        }
        return product;
    }

    public Product updateProduct(Product product, Integer productToUpdateId){
        Product productToUpdate = _productRepository.findById(productToUpdateId).orElse(null);
        if (productToUpdate != null) {
            if(product.getName() != null) {
                productToUpdate.setName(product.getName());
            }
            if(product.getShelfId() != null) {
                productToUpdate.setShelfId(product.getShelfId());
            }
            if(product.getAmountLeft() != null) {
                productToUpdate.setAmountLeft(product.getAmountLeft());
            }
            if(product.getDateOfExpiry() != null) {
                productToUpdate.setDateOfExpiry(product.getDateOfExpiry());
            }
            if(product.getStorageType() != null) {
                productToUpdate.setStorageType(product.getStorageType());
            }
            if(product.getDeliveryId() != null) {
                productToUpdate.setDeliveryId(product.getDeliveryId());
            }
            _productRepository.save(productToUpdate);
        }
        return productToUpdate;
    }

    public Product addProduct(Product product){
        return _productRepository.save(product);
    }

    public Product getProductById(Integer productId){
        return _productRepository.findById(productId).orElse(null);
    }
}
