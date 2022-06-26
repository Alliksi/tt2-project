package com.storage.shelf.repository;

import com.storage.shelf.domain.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShelfRepository extends JpaRepository<Shelf, Integer> {
}