package guru.springframework.webfluxrest.controller;

import guru.springframework.webfluxrest.domain.Category;
import guru.springframework.webfluxrest.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    Flux<Category> listAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> findById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> saveAll(@RequestBody Publisher<Category> category) {
        return categoryRepository.saveAll(category).then();
    }
}
