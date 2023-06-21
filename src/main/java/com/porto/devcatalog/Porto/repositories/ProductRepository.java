package com.porto.devcatalog.Porto.repositories;

import com.porto.devcatalog.Porto.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
