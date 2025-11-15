package com.paymentchain.product.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductEntity() {
        // Given
        Product product = new Product();

        // When
        product.setId(1L);
        product.setCode("PROD001");
        product.setName("Savings Account");

        // Then
        assertEquals(1L, product.getId());
        assertEquals("PROD001", product.getCode());
        assertEquals("Savings Account", product.getName());
    }

    @Test
    void testProductEquality() {
        // Given
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCode("PROD001");
        product1.setName("Checking Account");

        Product product2 = new Product();
        product2.setId(1L);
        product2.setCode("PROD001");
        product2.setName("Checking Account");

        // When/Then
        assertEquals(product1.getId(), product2.getId());
        assertEquals(product1.getCode(), product2.getCode());
        assertEquals(product1.getName(), product2.getName());
    }

    @Test
    void testProductDifferentValues() {
        // Given
        Product savings = new Product();
        savings.setCode("PROD001");
        savings.setName("Savings Account");

        Product checking = new Product();
        checking.setCode("PROD002");
        checking.setName("Checking Account");

        // When/Then
        assertNotEquals(savings.getCode(), checking.getCode());
        assertNotEquals(savings.getName(), checking.getName());
    }
}
