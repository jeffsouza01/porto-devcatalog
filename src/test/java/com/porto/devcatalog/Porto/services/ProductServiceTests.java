package com.porto.devcatalog.Porto.services;

import com.porto.devcatalog.Porto.DTO.ProductDTO;
import com.porto.devcatalog.Porto.entities.Category;
import com.porto.devcatalog.Porto.entities.Product;
import com.porto.devcatalog.Porto.repositories.CategoryRepository;
import com.porto.devcatalog.Porto.repositories.ProductRepository;
import com.porto.devcatalog.Porto.services.exceptions.DatabaseException;
import com.porto.devcatalog.Porto.services.exceptions.ResourceNotFoundExceptions;
import com.porto.devcatalog.Porto.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;
    private long existingID;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingID = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        category = Factory.createCategory();
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(productRepository.findById(existingID)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(productRepository.getReferenceById(existingID)).thenReturn(product);
        Mockito.when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getReferenceById(existingID)).thenReturn(category);
        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.doNothing().when(productRepository).deleteById(existingID);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }


    @Test
    public void findAllPagedShouldReturnPage(){
        Pageable page = PageRequest.of(0, 10);

        Page<ProductDTO> result = productService.findAllProducts(page);

        Assertions.assertNotNull(result);

        Mockito.verify(productRepository).findAll(page);

    }

    @Test
    public void updateShouldReturnProductDTOWhenIDExists(){

        ProductDTO result = productService.updateProduct(existingID, productDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIDDoesNotExists(){

        Assertions.assertThrows(ResourceNotFoundExceptions.class, () -> {
            productService.updateProduct(nonExistingId, productDTO);
        });
        Mockito.verify(productRepository).getReferenceById(nonExistingId);
    }


    @Test
    public void findByIdShouldReturnProductDTOWhenIDExists(){

        ProductDTO result = productService.findProductById(existingID);

        Assertions.assertNotNull(result);

        Mockito.verify(productRepository).findById(existingID);
    }



    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIDDoesNotExists(){

        Assertions.assertThrows(ResourceNotFoundExceptions.class, () -> {
                  productService.findProductById(nonExistingId);
        });
        Mockito.verify(productRepository).findById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependetId(){
        Assertions.assertThrows(DatabaseException.class, () -> {
           productService.deleteProduct(dependentId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIDDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundExceptions.class, () -> {
            productService.deleteProduct(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
    }


    @Test
    public void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() -> {
            productService.deleteProduct(existingID);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingID);
    }

}
