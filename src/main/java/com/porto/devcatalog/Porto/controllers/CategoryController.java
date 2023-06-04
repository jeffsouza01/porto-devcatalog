package com.porto.devcatalog.Porto.controllers;

import com.porto.devcatalog.Porto.entities.Category;
import com.porto.devcatalog.Porto.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public List<Category> findAllCategories(){
        return categoryService.findAllCategories();
    }

}
