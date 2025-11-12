package com.paymentchain.product.controller;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/all")
    private List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        Optional<Product> product  = productRepository.findById(id);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND).body(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<?> saveProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> modProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product newProduct = productOptional.get();
            newProduct.setCode(product.getCode());
            newProduct.setName(product.getName());
            return ResponseEntity.ok(productRepository.save(newProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        if ((productRepository.findById(id)).isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok(productRepository.findById(id));
        } else
            return ResponseEntity.notFound().build();
    }

}
