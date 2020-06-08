package guru.springframework.webfluxrest.controller;

import guru.springframework.webfluxrest.domain.Vendor;
import guru.springframework.webfluxrest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

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
}