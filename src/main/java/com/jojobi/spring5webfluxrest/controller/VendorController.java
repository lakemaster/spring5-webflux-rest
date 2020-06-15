package com.jojobi.spring5webfluxrest.controller;

import com.jojobi.spring5webfluxrest.domain.Vendor;
import com.jojobi.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> findById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }
}
