package com.storage.storage.service;

import com.storage.storage.domain.Storage;
import com.storage.storage.repository.StorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService implements IStorageService {

    private final StorageRepository _storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this._storageRepository = storageRepository;
    }

    @Override
    public List<Storage> getAllStoragesByRestaurantId(Integer restaurantId){
        return _storageRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public Storage addStorage(Storage storage) {
        return _storageRepository.save(storage);
    }

    @Override
    public Storage getStorageById(Integer storageId) {
        return _storageRepository.findById(storageId).orElse(null);
    }

    @Override
    public Storage deleteStorageById(Integer storageId) {
        Storage storage = _storageRepository.findById(storageId).orElse(null);
        if (storage != null) {
            _storageRepository.deleteById(storageId);
        }
        return storage;
    }

    @Override
    public Storage updateStorage(Storage storage, Integer id){
        Storage storageToUpdate = _storageRepository.findById(id).orElse(null);
        if (storageToUpdate != null) {
            if(storage.getName() != null) {
                storageToUpdate.setName(storage.getName());
            }
            if(storage.getRestaurantId() != null) {
                storageToUpdate.setRestaurantId(storage.getRestaurantId());
            }
            if(storage.getSize() != null) {
                storageToUpdate.setSize(storage.getSize());
            }
            _storageRepository.save(storageToUpdate);
        }
        return storageToUpdate;
    }

}
