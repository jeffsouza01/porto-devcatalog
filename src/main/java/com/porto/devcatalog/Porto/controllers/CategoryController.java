package com.porto.devcatalog.Porto.controllers;

import com.porto.devcatalog.Porto.DTO.CategoryDTO;
import com.porto.devcatalog.Porto.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CategoryDTO> createNewCategory(@RequestBody CategoryDTO dto) {
        dto = categoryService.createCategory(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        dto = categoryService.updateCategory(id, dto);

        return dto;
    }

}
