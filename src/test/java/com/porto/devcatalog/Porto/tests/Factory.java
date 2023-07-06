package com.porto.devcatalog.Porto.tests;

import com.porto.devcatalog.Porto.DTO.ProductDTO;
import com.porto.devcatalog.Porto.entities.Category;
import com.porto.devcatalog.Porto.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Phone Test",
                900.0, "https:img.com/img.png", Instant.parse("2023-07-05T10:00:00Z"));

        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory(){
        return new Category(2L, "Eletronics");
    }
}
