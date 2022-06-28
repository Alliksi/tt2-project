package com.storage.delivery.controller;

import com.storage.delivery.service.IDeliveryService;
import org.springframework.stereotype.Controller;

@Controller
public class DeliveryController {

    private final IDeliveryService _deliveryService;

    public DeliveryController(IDeliveryService deliveryService) {
        this._deliveryService = deliveryService;
    }


}