package com.storage.storage.repository;

import com.storage.storage.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StorageRepository extends JpaRepository<Storage, Integer> {
}