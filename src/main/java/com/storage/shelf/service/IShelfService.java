package com.storage.shelf.service;

import com.storage.shelf.domain.Shelf;

import java.util.List;

public interface IShelfService {
    List<Shelf> getAll();

    Shelf deleteShelfById(Integer shelfId);
}
