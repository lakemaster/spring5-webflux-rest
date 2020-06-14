package com.jojobi.spring5webfluxrest.bootstrap;

import com.jojobi.spring5webfluxrest.domain.Category;
import com.jojobi.spring5webfluxrest.domain.Vendor;
import com.jojobi.spring5webfluxrest.repositories.CategoryRepository;
import com.jojobi.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"default", "test"})
public class TestDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public TestDataLoader(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {

        categoryRepository.save(Category.builder()
                .description("Cat1")
                .build()).block();

        log.debug("Categories loaded: {}", categoryRepository.count().block() );
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder()
                .firstName("Hans")
                .lastName("Wurst")
                .build()).block();

        log.debug("Vendors loaded: {}", vendorRepository.count().block() );
    }
}
