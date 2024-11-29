package com.freyja.spellbreaker.controllers;

import com.freyja.spellbreaker.entities.Customer;
import com.freyja.spellbreaker.entities.Device;
import com.freyja.spellbreaker.repos.CustomerRepository;
import com.freyja.spellbreaker.repos.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private DeviceRepository deviceRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/")
    public String greeting(Model model) {

        Iterable<Device> devices = deviceRepo.findAll();
        model.addAttribute("devices", devices);

        return "home";
    }

    @GetMapping("/add-device")
    public String addDevice() {
        return "add-device";
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<Map<String, String>> autocomplete(@RequestParam String query, @RequestParam String type) {
        List<Customer> customers = (List<Customer>) customerRepo.findAll();

        return customers.stream()
                .filter(customer -> (type.equals("email") && customer.getEmail().contains(query)) ||
                        (type.equals("phone") && customer.getPhoneNumber().contains(query)))
                .map(customer -> Map.of(
                        "name", customer.getCustomerName(),
                        "email", customer.getEmail(),
                        "phoneNumber", customer.getPhoneNumber()
                ))
                .toList();
    }

}