package com.storage.delivery.controller;

import com.storage.delivery.service.IDeliveryService;
import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeliveryController {

    private final IDeliveryService _deliveryService;

    public DeliveryController(IDeliveryService deliveryService) {
        this._deliveryService = deliveryService;
    }


}