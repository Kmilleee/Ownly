package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.repository.rowMapper.ItemSoldRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ItemSoldRepositorySql implements ItemSoldRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemSoldRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public void createItemSold(ItemSold itemSold) {
        String sql = "INSERT INTO ItemSold (articleName,image, description, auctionStartDate, auctionEndDate, startingPrice, priceSale, user_id, category_id) VALUES (:articleName,:image, :description, :auctionStartDate, :auctionEndDate, :startingPrice, :priceSale, :user_id, :category_id)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("articleName", itemSold.getArticleName());
        params.addValue("image", itemSold.getImage());
        params.addValue("description", itemSold.getDescription());
        params.addValue("auctionStartDate", itemSold.getAuctionStartDate());
        params.addValue("auctionEndDate", itemSold.getAuctionEndDate());
        params.addValue("startingPrice", itemSold.getStartingPrice());
        params.addValue("priceSale", itemSold.getPriceSale());
        params.addValue("user_id", itemSold.getSeller().getUser_id());
        params.addValue("category_id", itemSold.getCategory().getCategory_id());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        if (keyHolder.getKey() != null) {
            itemSold.setId(keyHolder.getKey().longValue());
        }

        if (itemSold.getWithdrawal() != null) {

            MapSqlParameterSource paramsWithdrawal = new MapSqlParameterSource();

            paramsWithdrawal.addValue("street", itemSold.getWithdrawal().getStreet());
            paramsWithdrawal.addValue("postalCode", itemSold.getWithdrawal().getPostalCode());
            paramsWithdrawal.addValue("city", itemSold.getWithdrawal().getCity());
            paramsWithdrawal.addValue("article_id", itemSold.getId());

            String sqlWithdrawal = "INSERT INTO WITHDRAWAL (street, postalCode, city, article_id) VALUES (:street, :postalCode, :city, :article_id)";

            namedParameterJdbcTemplate.update(sqlWithdrawal, paramsWithdrawal);

        }

    }

    @Override
    public List<ItemSold> readItemSold() {
        String sql = "SELECT a.article_id as article_id, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, u.username as username \n" +
                "FROM ItemSold a\n" +
                "INNER JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "INNER JOIN USERS u ON a.user_id = u.user_id\n";


        return jdbcTemplate.query(sql, new ItemSoldRowMapper());
    }

    @Transactional
    @Override
    public void updateItemSold(ItemSold itemSold) {

        String sql = "UPDATE ItemSold SET articleName = :articleName, image = :image, description = :description, auctionStartDate = :auctionStartDate, auctionEndDate = :auctionEndDate, startingPrice = :startingPrice, priceSale = :priceSale WHERE article_id = :id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(itemSold);

        namedParameterJdbcTemplate.update(sql, map);

        if (itemSold.getWithdrawal() != null) {
            String sqlWithdrawal = "UPDATE WITHDRAWAL SET street = :street, postalCode = :postalCode, city = :city WHERE article_id = :article_id";

            MapSqlParameterSource paramsW = new MapSqlParameterSource();
            paramsW.addValue("street", itemSold.getWithdrawal().getStreet());
            paramsW.addValue("postalCode", itemSold.getWithdrawal().getPostalCode());
            paramsW.addValue("city", itemSold.getWithdrawal().getCity());
            paramsW.addValue("article_id", itemSold.getId());

            namedParameterJdbcTemplate.update(sqlWithdrawal, paramsW);
        }
    }

    @Transactional
    @Override
    public void deleteItemSold(long article_id) {
        String sqlWithdrawal = "DELETE FROM WITHDRAWAL WHERE article_id = :article_id";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("article_id", article_id);
        namedParameterJdbcTemplate.update(sqlWithdrawal, map);

        String sqlItem = "DELETE FROM ItemSold WHERE article_id = :article_id";
        namedParameterJdbcTemplate.update(sqlItem, map);
    }

    @Override
    public ItemSold readItemById(long article_id) {

        String sql = "SELECT a.article_id as article_id, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, u.username as username \n" +
                "FROM ItemSold a\n" +
                "INNER JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "INNER JOIN USERS u ON a.user_id = u.user_id\n" +
                "WHERE a.article_id =:article_id  ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("article_id", article_id);

        return namedParameterJdbcTemplate.queryForObject(sql, map, new ItemSoldRowMapper());
    }

    @Override
    public List<ItemSold> readItemsBySeller(long sellerId) {
        String sql = "SELECT * FROM ItemSold WHERE user_id = :sellerId";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("sellerId", sellerId);

        return namedParameterJdbcTemplate.query(sql, map, new ItemSoldRowMapper());
    }

    @Override
    public ItemSold readItemSoldById(long article_id) {
        return null;
    }
}
