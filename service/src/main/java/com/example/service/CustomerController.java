package com.example.service;

import io.arconia.multitenancy.core.context.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@ResponseBody
class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    // TODO: Put DB layer in a component
    private final CustomerRepository customerRepository;

    CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    Collection<Customer> customers() {
        logger.info("Geting customers for tenant {}", TenantContext.getTenantIdentifier());
        return customerRepository.findAll();
    }
}
