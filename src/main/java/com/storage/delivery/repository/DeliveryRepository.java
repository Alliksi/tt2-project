package com.storage.delivery.repository;

import com.storage.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}