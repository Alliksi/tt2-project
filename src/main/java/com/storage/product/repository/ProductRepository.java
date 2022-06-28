package com.storage.product.repository;

import com.storage.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByShelfId(Integer shelfId);
}