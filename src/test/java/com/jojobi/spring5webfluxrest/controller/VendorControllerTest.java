package com.jojobi.spring5webfluxrest.controller;

import com.jojobi.spring5webfluxrest.domain.Vendor;
import com.jojobi.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class VendorControllerTest {

    VendorRepository vendorRepository;
    VendorController vendorController;
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void findAll() {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("FN1").lastName("LN1").build(),
                        Vendor.builder().firstName("FN2").lastName("LN2").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void findById() {
        Vendor vendor =Vendor.builder().firstName("FN").lastName("LN").build();

        given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(vendor));

        webTestClient.get()
                .uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class)
                .isEqualTo(vendor);
    }

    @Test
    void create() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("FN").lastName("LN").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        Vendor vendor = Vendor.builder().firstName("FN").lastName("LN").build();

        given(vendorRepository.save(eq(vendor)))
                .willReturn(Mono.just(vendor));

        webTestClient.put()
                .uri("/api/v1/vendors/someId")
                .bodyValue(vendor)
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void patch() {
        Vendor dbVendor = Vendor.builder().firstName("fn1").lastName("ln1").build();
        Vendor patchVendor = Vendor.builder().firstName("fn2").build();
        Vendor expectedResultVendor = Vendor.builder().firstName("fn2").lastName("ln1").build();

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(dbVendor));

        given(vendorRepository.save(any()))
                .willAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        FluxExchangeResult<Vendor> result = webTestClient.patch()
                .uri("/api/v1/vendors/someId")
                .bodyValue(patchVendor)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Vendor.class);

        StepVerifier.create(result.getResponseBody())
                .expectNext(expectedResultVendor)
                .thenCancel()
                .verify();

        verify(vendorRepository, times(1)).save(any());
    }

    @Test
    void patchNotFound() {
        Vendor patchVendor = Vendor.builder().firstName("fn2").build();

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.empty());

        given(vendorRepository.save(any()))
                .willAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        FluxExchangeResult<Vendor> result = webTestClient.patch()
                .uri("/api/v1/vendors/someId")
                .bodyValue(patchVendor)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Vendor.class);

        StepVerifier.create(result.getResponseBody())
                .expectComplete()
                .verify();

        verify(vendorRepository, never()).save(any());
    }


}