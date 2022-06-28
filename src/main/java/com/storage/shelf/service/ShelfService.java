package com.storage.shelf.service;

import com.storage.shelf.domain.Shelf;
import com.storage.shelf.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfService implements IShelfService {
    private final ShelfRepository _shelfRepository;

    public ShelfService(ShelfRepository shelfRepository) {
        this._shelfRepository = shelfRepository;
    }

    @Override
    public List<Shelf> getAllByStorageId(Integer storageId) {
        return _shelfRepository.findAllByStorageId(storageId);
    }

    @Override
    public Shelf deleteShelfById(Integer shelfId) {
        Shelf shelf = _shelfRepository.findById(shelfId).orElse(null);
        if (shelf != null) {
            _shelfRepository.deleteById(shelfId);
        }
        return shelf;
    }

    @Override
    public Shelf addShelf(Shelf shelf) {
        return _shelfRepository.save(shelf);
    }

    @Override
    public Shelf getShelfById(Integer shelfId) {
        return _shelfRepository.findById(shelfId).orElse(null);
    }

    @Override
    public Shelf updateShelf(Shelf shelf, Integer shelfToUpdateId) {
        Shelf shelfToUpdate = _shelfRepository.findById(shelfToUpdateId).orElse(null);
        if (shelfToUpdate != null) {
            if(shelf.getName() != null) {
                shelfToUpdate.setName(shelf.getName());
            }
            if(shelf.getStorageId() != null) {
                shelfToUpdate.setStorageId(shelf.getStorageId());
            }
            if(shelf.getAllocatedSpace() != null) {
                shelfToUpdate.setAllocatedSpace(shelf.getAllocatedSpace());
            }
            if(shelf.getStorageType () != null) {
                shelfToUpdate.setStorageType(shelf.getStorageType());
            }
            if(shelf.getStorageId() != null) {
                shelfToUpdate.setStorageId(shelf.getStorageId());
            }
            _shelfRepository.save(shelfToUpdate);
        }
        return shelfToUpdate;
    }
}
