package com.storage.storage.service;

import com.storage.storage.domain.Storage;

import java.util.List;

public interface IStorageService {
    List<Storage> getAllStoragesByRestaurantId(Integer restaurantId);

    Storage addStorage(Storage storage);

    Storage getStorageById(Integer storageId);

    Storage deleteStorageById(Integer storageId);

    Storage updateStorage(Storage storage, Integer id);

}
