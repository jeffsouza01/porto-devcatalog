package com.porto.devcatalog.Porto.repositories;

import com.porto.devcatalog.Porto.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
