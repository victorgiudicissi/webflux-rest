package guru.springframework.webfluxrest.controller;

import guru.springframework.webfluxrest.domain.Vendor;
import guru.springframework.webfluxrest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    public final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    Flux<Vendor> listAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Vendor> findById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> saveAll(@RequestBody Publisher<Vendor> vendor) {
        return vendorRepository.saveAll(vendor).then();
    }
}
