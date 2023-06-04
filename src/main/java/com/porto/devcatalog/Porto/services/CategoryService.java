package com.porto.devcatalog.Porto.services;

import com.porto.devcatalog.Porto.DTO.CategoryDTO;
import com.porto.devcatalog.Porto.entities.Category;
import com.porto.devcatalog.Porto.repositories.CategoryRepository;
import com.porto.devcatalog.Porto.services.exceptions.ResourceNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(x-> new CategoryDTO(x)).toList();
    }


    @Transactional(readOnly = true)
    public CategoryDTO findCategoriesById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundExceptions("Item not found!"));


        return new CategoryDTO(category);
    }


    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category = categoryRepository.save(category);

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {

        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(dto.getName());
            category = categoryRepository.save(category);

            return new CategoryDTO(category);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundExceptions("Category not found with ID: " + id);
        }

    }
}
