package com.storage.delivery_company.repository;

import com.storage.delivery_company.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}