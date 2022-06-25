package com.storage.product.database.repository;

import com.storage.product.database.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}