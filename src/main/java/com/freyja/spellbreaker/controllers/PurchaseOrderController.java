package com.freyja.spellbreaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseOrderController {

    @GetMapping("/purchase-orders")
    public String purchaseOrder() {
        return "purchase-orders";
    }
}
