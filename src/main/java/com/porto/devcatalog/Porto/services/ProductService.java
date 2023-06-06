package com.porto.devcatalog.Porto.services;

import com.porto.devcatalog.Porto.DTO.ProductDTO;
import com.porto.devcatalog.Porto.entities.Product;
import com.porto.devcatalog.Porto.repositories.ProductRepository;
import com.porto.devcatalog.Porto.services.exceptions.DatabaseException;
import com.porto.devcatalog.Porto.services.exceptions.ResourceNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllCategories(PageRequest pageRequest) {
        Page<Product> categories = productRepository.findAll(pageRequest);

        return categories.map(x-> new ProductDTO(x));
    }


    @Transactional(readOnly = true)
    public ProductDTO findCategoriesById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundExceptions("Item not found!"));


        return new ProductDTO(product, product.getCategories());
    }


    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product = productRepository.save(product);

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto) {

        try {
            Product product = productRepository.getReferenceById(id);
            product.setName(dto.getName());
            product = productRepository.save(product);

            return new ProductDTO(product);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundExceptions("Product not found with ID: " + id);
        }

    }


    @Transactional
    public void deleteProduct(Long id){
        try {
            productRepository.deleteById(id);

        } catch (EmptyResultDataAccessException err) {
            throw new ResourceNotFoundExceptions("Id not found " + id);
        } catch (DataIntegrityViolationException err) {
            throw new DatabaseException("Error Database Integration");
        }

    }
}
