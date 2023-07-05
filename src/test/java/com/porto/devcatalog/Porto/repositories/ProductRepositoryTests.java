package com.porto.devcatalog.Porto.repositories;

import com.porto.devcatalog.Porto.entities.Product;
import com.porto.devcatalog.Porto.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private long existingID;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingID = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWhithAutoIcrement(){
        Product product = Factory.createProduct();

        product.setId(null);
        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts+1, product.getId());

    }

    @Test
    public void findByIdShouldReturnExistProductWithExistId(){

        Optional<Product> product = productRepository.findById(existingID);

        Assertions.assertTrue(product.isPresent());

    }

    @Test
    public void findByIdShouldReturnEmptyProductWithNonExistId(){
        Optional<Product> product = productRepository.findById(nonExistingId);

        Assertions.assertTrue(product.isEmpty());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        productRepository.deleteById(existingID);

        Optional<Product> result = productRepository.findById(existingID);

        Assertions.assertFalse(result.isPresent());

    }


    @Test
    public void deleteShouldThrowEmptResultDataAcessExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
           productRepository.deleteById(nonExistingId);
        });

    }

}
