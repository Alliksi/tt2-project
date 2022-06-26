package com.storage.storage.service;

import com.storage.storage.domain.Storage;
import com.storage.storage.repository.StorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService implements IStorageService {
    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Override
    public List<Storage> getAll() {
        return storageRepository.findAll();
    }
}
