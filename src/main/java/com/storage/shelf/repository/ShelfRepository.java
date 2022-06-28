package com.storage.shelf.repository;

import com.storage.shelf.domain.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShelfRepository extends JpaRepository<Shelf, Integer> {
    List<Shelf> findAllByStorageId(Integer storageId);
}