package com.paymentchain.product.repository;

import com.paymentchain.product.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindById() {
        // Given
        Product product = new Product();
        product.setCode("PROD001");
        product.setName("Savings Account");

        Product saved = entityManager.persistAndFlush(product);

        // When
        Optional<Product> found = productRepository.findById(saved.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("PROD001", found.get().getCode());
        assertEquals("Savings Account", found.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        // When
        Optional<Product> found = productRepository.findById(999L);

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void testSaveProduct() {
        // Given
        Product product = new Product();
        product.setCode("PROD002");
        product.setName("Checking Account");

        // When
        Product saved = productRepository.save(product);

        // Then
        assertNotNull(saved.getId());
        assertEquals("PROD002", saved.getCode());
        assertEquals("Checking Account", saved.getName());
    }

    @Test
    void testFindAll() {
        // Given
        Product product1 = new Product();
        product1.setCode("PROD003");
        product1.setName("Credit Card");

        Product product2 = new Product();
        product2.setCode("PROD004");
        product2.setName("Debit Card");

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);

        // When
        List<Product> products = productRepository.findAll();

        // Then
        assertTrue(products.size() >= 2);
        assertTrue(products.stream().anyMatch(p -> p.getCode().equals("PROD003")));
        assertTrue(products.stream().anyMatch(p -> p.getCode().equals("PROD004")));
    }

    @Test
    void testDeleteProduct() {
        // Given
        Product product = new Product();
        product.setCode("PROD005");
        product.setName("Mortgage");

        Product saved = entityManager.persistAndFlush(product);
        Long productId = saved.getId();

        // When
        productRepository.deleteById(productId);
        entityManager.flush();

        // Then
        Optional<Product> found = productRepository.findById(productId);
        assertFalse(found.isPresent());
    }

    @Test
    void testUpdateProduct() {
        // Given
        Product product = new Product();
        product.setCode("PROD006");
        product.setName("Personal Loan");

        Product saved = entityManager.persistAndFlush(product);

        // When
        saved.setName("Updated Personal Loan");
        Product updated = productRepository.save(saved);
        entityManager.flush();

        // Then
        assertEquals("Updated Personal Loan", updated.getName());
        assertEquals("PROD006", updated.getCode());
    }
}
