package guru.springframework.webfluxrest.controller;

import guru.springframework.webfluxrest.domain.Category;
import guru.springframework.webfluxrest.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.mockito.Mockito.*;

import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    WebTestClient webTestClient = null;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeAll
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void listAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(
                        Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri("/api/v1/category")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void findById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Category.builder().description("Cat1").build()));

        webTestClient.get()
                .uri("/api/v1/category/abc")
                .exchange()
                .expectBody(Category.class);
    }
}