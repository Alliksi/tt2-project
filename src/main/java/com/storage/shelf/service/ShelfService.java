package com.storage.shelf.service;

import com.storage.shelf.domain.Shelf;
import com.storage.shelf.repository.ShelfRepository;
import com.storage.storage.domain.Storage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfService implements IShelfService {
    private final ShelfRepository _shelfRepository;

    public ShelfService(ShelfRepository shelfRepository) {
        this._shelfRepository = shelfRepository;
    }

    @Override
    public List<Shelf> getAll() {
        return _shelfRepository.findAll();
    }

    @Override
    public Shelf deleteShelfById(Integer shelfId){
        Shelf shelf = _shelfRepository.findById(shelfId).orElse(null);
        if (shelf != null) {
            _shelfRepository.deleteById(shelfId);
        }
        return shelf;
    }
}
