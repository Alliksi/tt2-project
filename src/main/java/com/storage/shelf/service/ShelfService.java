package com.storage.shelf.service;

import com.storage.shelf.domain.Shelf;
import com.storage.shelf.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfService implements IShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    @Override
    public List<Shelf> getAll() {
        return shelfRepository.findAll();
    }
}
