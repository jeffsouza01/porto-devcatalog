package com.porto.devcatalog.Porto.controllers;

import com.porto.devcatalog.Porto.DTO.CategoryDTO;
import com.porto.devcatalog.Porto.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public List<CategoryDTO> findAllCategories(){
        return categoryService.findAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO findCategoryById(@PathVariable Long id){
        return categoryService.findCategoriesById(id);
    }


}
