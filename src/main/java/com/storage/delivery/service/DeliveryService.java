package com.storage.delivery.service;

import com.storage.delivery.domain.Delivery;
import com.storage.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService implements IDeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }
}
