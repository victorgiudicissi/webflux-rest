package guru.springframework.webfluxrest.bootstrap;

import guru.springframework.webfluxrest.domain.Category;
import guru.springframework.webfluxrest.domain.Vendor;
import guru.springframework.webfluxrest.repository.CategoryRepository;
import guru.springframework.webfluxrest.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            log.info("Loading data");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            log.info("Loaded categories: {}", categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Victor").lastName("Hugo").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Laura").lastName("Beatriz").build()).block();

            log.info("Loaded vendors: {}", vendorRepository.count().block());

        }
    }
}
