package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.bo.Category;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemSoldRowMapper implements RowMapper<ItemSold> {

    @Override
    public ItemSold mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemSold article = new ItemSold();
        article.setId(rs.getLong("article_id"));
        article.setArticleName(rs.getString("name_article"));
        article.setDescription(rs.getString("description"));
        article.setStartingPrice(rs.getLong("startingPrice"));
        article.setImage(rs.getString("image"));


        Category cat = new Category();
        cat.setCategory_id(rs.getLong("category_id"));
        cat.setName(rs.getString("nom_cat"));

        User user = new User();
        user.setUsername(rs.getString("username"));

        article.setSeller(user);
        article.setCategory(cat);

        return article;
    }
}