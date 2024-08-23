package com.i2i.zing.repository;

import com.i2i.zing.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByIsDeletedFalse();

    Category findByIsDeletedFalseAndCategoryId(String categoryId);
}
