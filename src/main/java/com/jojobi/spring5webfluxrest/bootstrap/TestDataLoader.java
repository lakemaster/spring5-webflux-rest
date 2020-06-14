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

        if (categoryRepository.count().block() == 0) {
            //load data
            log.debug("#### LOADING DATA ON BOOTSTRAP #####");

            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();

            log.debug("Loaded Categories: {}", categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Joe")
                    .lastName("Buck").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Micheal")
                    .lastName("Weston").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jessie")
                    .lastName("Waters").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Bill")
                    .lastName("Nershi").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jimmy")
                    .lastName("Buffett").build()).block();

            log.debug("Loaded Vendors: {}", vendorRepository.count().block());

        }
    }
}
