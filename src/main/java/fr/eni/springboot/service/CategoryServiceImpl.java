package fr.eni.springboot.service;

import fr.eni.springboot.bo.Category;
import fr.eni.springboot.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.createCategory(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category readCatById(long category_id) {
        return categoryRepository.readCatById(category_id);
    }

    @Override
    public void updateCat(Category category) {
        categoryRepository.updateCat(category);
    }

    @Override
    public void deleteCat(long category_id) {
        categoryRepository.deleteCat(category_id);
    }
}

