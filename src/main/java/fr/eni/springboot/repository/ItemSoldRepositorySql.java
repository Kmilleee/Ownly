package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.repository.rowMapper.ItemSoldRowMapper;
import fr.eni.springboot.repository.rowMapper.UtilisateurRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql = "SELECT article_id as no_article, image as image, user_id, category_id, article_id, articleName as nom_article, description as description, auctionEndDate, auctionStartDate, startingPrice as prix_initial, priceSale from ItemSold";


        return jdbcTemplate.query(sql, new ItemSoldRowMapper());
    }

}
