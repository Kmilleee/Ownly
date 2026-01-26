package fr.eni.springboot.service;

import fr.eni.springboot.bo.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category);

    List<Category> findAll();

    Category readCatById(long category_id);

    void updateCat(Category category);

    void deleteCat(long category_id);
}
