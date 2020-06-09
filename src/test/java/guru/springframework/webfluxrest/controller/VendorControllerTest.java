package guru.springframework.webfluxrest.controller;

import guru.springframework.webfluxrest.domain.Category;
import guru.springframework.webfluxrest.domain.Vendor;
import guru.springframework.webfluxrest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeAll
    public void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void listAll() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder().firstName("V1").lastName("L1").build(),
                        Vendor.builder().firstName("V2").lastName("L2").build()
                        ));

        webTestClient
                .get()
                .uri("/api/v1/vendor")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void findById() {
        BDDMockito.given(vendorRepository.findById(Mockito.anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("v1").lastName("l1").build()));

        webTestClient
                .get()
                .uri("/api/v1/vendor/abc")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void saveAll() {
        BDDMockito.given(vendorRepository.saveAll((Publisher<Vendor>) Mockito.any()))
                .willReturn(Flux.just(Vendor.builder().firstName("v1").lastName("l1").build()));

        webTestClient
                .post()
                .uri("/api/v1/vendor")
                .body(Mono.just(Vendor.builder().firstName("V!").lastName("L1").build()), Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update() {
        BDDMockito.given(vendorRepository.save(any()))
                .willReturn(Mono.just(Vendor.builder().firstName("V1").lastName("V2").build()));

        webTestClient.put()
                .uri("/api/v1/vendor")
                .body(Mono.just(Category.builder().description("C2").build()), Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}