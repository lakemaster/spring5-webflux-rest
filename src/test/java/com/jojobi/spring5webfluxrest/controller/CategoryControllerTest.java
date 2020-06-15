package com.jojobi.spring5webfluxrest.controller;

import com.jojobi.spring5webfluxrest.domain.Category;
import com.jojobi.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;


    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void testFindAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void testFindById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/someId")
                .exchange()
                .expectBody(Category.class)
                .value( category -> {
                    if ( ! category.getDescription().equals("Cat1"))
                        throw new RuntimeException("wrong data");
                });
    }
}