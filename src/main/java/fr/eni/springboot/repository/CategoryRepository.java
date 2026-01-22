package fr.eni.springboot.repository;


import fr.eni.springboot.bo.Category;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CategoryRepository {
    private final DataSource dataSource;

    public CategoryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void insert (Category category) {
        String sql = "INSERT INTO category (libelle) VALUES (?) ";
    }

}
