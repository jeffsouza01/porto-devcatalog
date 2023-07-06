package com.porto.devcatalog.Porto.services;

import com.porto.devcatalog.Porto.DTO.CategoryDTO;
import com.porto.devcatalog.Porto.DTO.ProductDTO;
import com.porto.devcatalog.Porto.entities.Category;
import com.porto.devcatalog.Porto.entities.Product;
import com.porto.devcatalog.Porto.repositories.CategoryRepository;
import com.porto.devcatalog.Porto.repositories.ProductRepository;
import com.porto.devcatalog.Porto.services.exceptions.DatabaseException;
import com.porto.devcatalog.Porto.services.exceptions.ResourceNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> categories = productRepository.findAll(pageable);

        return categories.map(x-> new ProductDTO(x));
    }


    @Transactional(readOnly = true)
    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundExceptions("Item not found!"));


        return new ProductDTO(product, product.getCategories());
    }


    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = new Product();
        copyDTO2Entity(productDTO, product);

        product = productRepository.save(product);

        return new ProductDTO(product, product.getCategories());
    }



    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {

        try {
            Product product = productRepository.getReferenceById(id);
            copyDTO2Entity(productDTO, product);

            product = productRepository.save(product);

            return new ProductDTO(product, product.getCategories());
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundExceptions("Product not found with ID: " + id);
        }

    }


    public void deleteProduct(Long id){
        try {
            productRepository.deleteById(id);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Id not found " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundExceptions("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error Database Integration");
        }

    }

    private void copyDTO2Entity(ProductDTO productDTO, Product product) {

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
        product.setDate(productDTO.getDate());

        product.getCategories().clear();

        for (CategoryDTO catDTO : productDTO.getCategories()) {
//            Category category = categoryRepository.getReferenceById(catDTO.getId());
            product.getCategories().add(new Category(catDTO.getId(), catDTO.getName()));
        }
    }

}
