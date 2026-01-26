package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository {

    void createCategory(Category category);

    List<Category> findAll();

    Category readCatById(long category_id);

    void updateCat(Category category);

    void deleteCat(long category_id);
}
