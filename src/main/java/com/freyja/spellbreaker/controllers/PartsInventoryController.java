package com.freyja.spellbreaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PartsInventoryController {

    @GetMapping("/parts")
    public String parts() {
        return "parts";
    }

}
