package com.storage.shelf.service;

import com.storage.shelf.domain.Shelf;

import java.util.List;

public interface IShelfService {
    List<Shelf> getAllByStorageId(Integer storageId);

    Shelf addShelf(Shelf shelf);

    Shelf getShelfById(Integer shelfId);

    Shelf deleteShelfById(Integer shelfId);

    Shelf updateShelf(Shelf shelf, Integer shelfToUpdateId);
}
