package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.Rarity;
import fr.eni.springboot.repository.rowMapper.ItemSoldRowMapper;
import fr.eni.springboot.repository.rowMapper.ItemSoldRowMapper2;
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
        String sql = "INSERT INTO ItemSold (articleName, image, description, auctionStartDate, " +
                "auctionEndDate, startingPrice, priceSale, user_id, category_id, rarity) " +
                "VALUES (:articleName, :image, :description, :auctionStartDate, :auctionEndDate, :startingPrice, " +
                " :priceSale, :user_id, :category_id, :rarity)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("articleName", itemSold.getArticleName());
        params.addValue("image", itemSold.getImage());
        params.addValue("description", itemSold.getDescription());
        params.addValue("auctionStartDate", itemSold.getAuctionStartDate() != null ?
                java.sql.Timestamp.valueOf(itemSold.getAuctionStartDate()) : null);
        params.addValue("auctionEndDate", itemSold.getAuctionEndDate() != null ?
                java.sql.Timestamp.valueOf(itemSold.getAuctionEndDate()) : null);
        params.addValue("startingPrice", itemSold.getStartingPrice());
        params.addValue("priceSale", itemSold.getPriceSale());
        params.addValue("user_id", itemSold.getSeller() != null ? itemSold.getSeller().getUser_id() : null);
        params.addValue("category_id", itemSold.getCategory() != null ? itemSold.getCategory().getCategory_id() : null);
        params.addValue("rarity", itemSold.getRarity() != null ? itemSold.getRarity().name() : "COMMON");

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
        String sql = "SELECT  a.article_id as article_id,rarity as rarity, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, a.user_id as user_id, u.username as username, u.avatar as avatar, w.postalCode as postalCode, w.street as street,w.city as city \n" +
                "FROM ItemSold a\n" +
                "left JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "left JOIN USERS u ON a.user_id = u.user_id\n" +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id ";


        return jdbcTemplate.query(sql, new ItemSoldRowMapper());
    }

    @Transactional
    @Override
    public void updateItemSold(ItemSold itemSold) {
        String sql = "UPDATE ItemSold SET articleName = :articleName, image = :image, description = :description, " +
                "auctionStartDate = :auctionStartDate, auctionEndDate = :auctionEndDate, " +
                "startingPrice = :startingPrice, priceSale = :priceSale, rarity = :rarity " +
                "WHERE article_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", itemSold.getId());
        params.addValue("articleName", itemSold.getArticleName());
        params.addValue("image", itemSold.getImage());
        params.addValue("description", itemSold.getDescription());
        params.addValue("auctionStartDate", itemSold.getAuctionStartDate() != null ? java.sql.Timestamp.valueOf(itemSold.getAuctionStartDate()) : null);
        params.addValue("auctionEndDate", itemSold.getAuctionEndDate() != null ? java.sql.Timestamp.valueOf(itemSold.getAuctionEndDate()) : null);
        params.addValue("startingPrice", itemSold.getStartingPrice());
        params.addValue("priceSale", itemSold.getPriceSale());
        params.addValue("rarity", itemSold.getRarity() != null ? itemSold.getRarity().name() : "COMMON");

        namedParameterJdbcTemplate.update(sql, params);

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
        //String sql = "SELECT a.article_id, a.articleName, a.startingPrice, a.priceSale, a.auctionStartDate, a.auctionEndDate, a.description, a.category_id, a.image, c.name as name, u.username as username FROM ItemSold a LEFT JOIN CATEGORY c ON a.category_id = c.category_id INNER JOIN USERS u ON a.user_id = u.user_id WHERE a.article_id = :article_id";

        String sql = "SELECT a.article_id as article_id,rarity as rarity, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, a.user_id as user_id, u.username as username, u.avatar as avatar, w.postalCode as postalCode, w.street as street,w.city as city \n" +
                "FROM ItemSold a\n" +
                "left JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "left JOIN USERS u ON a.user_id = u.user_id\n" +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id " +
                "WHERE a.article_id =:article_id  ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("article_id", article_id);

        // queryForObject lance une exception si 0 résultat est trouvé
        return namedParameterJdbcTemplate.queryForObject(sql, map, new ItemSoldRowMapper());
    }

    @Override
    public List<ItemSold> readItemsBySeller(long sellerId) {
        //String sql = "SELECT a.*, c.name as name, u.username as username FROM ItemSold a LEFT JOIN CATEGORY c ON a.category_id = c.category_id INNER JOIN USERS u ON a.user_id = u.user_id WHERE a.user_id = :sellerId";

        String sql = "SELECT a.article_id as article_id,rarity as rarity, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, a.user_id as user_id, u.username as username, u.avatar as avatar, w.postalCode as postalCode, w.street as street,w.city as city \n" +
                "FROM ItemSold a\n" +
                "left JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "left JOIN USERS u ON a.user_id = u.user_id\n" +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id " +
                "WHERE a.user_id =:sellerId  ";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("sellerId", sellerId);

        return namedParameterJdbcTemplate.query(sql, map, new ItemSoldRowMapper());
    }

    @Override
    public List<ItemSold> findByRarity(Rarity rarity) {
        String sql = "SELECT a.article_id as article_id,rarity as rarity, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, a.user_id as user_id, u.username as username, u.avatar as avatar, w.postalCode as postalCode, w.street as street,w.city as city \n" +
                "FROM ItemSold a\n" +
                "left JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "left JOIN USERS u ON a.user_id = u.user_id\n" +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id " +
                "WHERE rarity =:rarity  ";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("rarity", rarity.name());

        return namedParameterJdbcTemplate.query(sql, map, new ItemSoldRowMapper());
    }

    @Override
    public List<ItemSold> readItemsByBetterSel() {
        String sql = "SELECT TOP 4" +
                "  MAX(a.article_id) as article_id, " +
                " MAX(a.rarity) as rarity, " +
                "  MAX(a.articleName) as articleName, " +
                "  MAX(a.startingPrice) as startingPrice, " +
                " MAX(a.priceSale) as priceSale, " +
                " MAX(a.auctionStartDate) as auctionStartDate, " +
                " MAX(a.auctionEndDate) as auctionEndDate, " +
                "MAX(a.description) as description, " +
                " MAX(a.category_id) as category_id, " +
                "MAX(a.image) as image, " +
                " MAX(c.name) as name, " +
                "  a.user_id, " +
                "  u.username, " +
                " u.avatar, " +
                "MAX(w.postalCode) as postalCode, " +
                "  MAX(w.street) as street, " +
                " MAX(w.city) as city " +
                "FROM ItemSold a " +
                "LEFT JOIN CATEGORY c ON a.category_id = c.category_id " +
                "LEFT JOIN USERS u ON a.user_id = u.user_id " +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id " +
                "GROUP BY a.user_id, u.username, u.avatar " +
                "ORDER BY a.user_id DESC";


        return jdbcTemplate.query(sql, new ItemSoldRowMapper());
    }

    @Override
    public List<ItemSold> readItemBySearch(String query) {
        String sql = "SELECT a.article_id as article_id,rarity as rarity, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, a.user_id as user_id, u.username as username, u.avatar as avatar, w.postalCode as postalCode, w.street as street,w.city as city \n" +
                "FROM ItemSold a\n" +
                "left JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "left JOIN USERS u ON a.user_id = u.user_id\n" +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id " +
                "where a.articleName Like :query";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("query", "%" + query + "%");

        return namedParameterJdbcTemplate.query(sql, map, new ItemSoldRowMapper());

    }

    @Override
    public List<ItemSold> readItemByCategory(String cat) {
        String sql = "SELECT a.article_id as article_id,rarity as rarity, a.articleName as articleName, a.startingPrice as startingPrice, a.priceSale as priceSale, a.auctionStartDate as auctionStartDate, a.auctionEndDate as auctionEndDate, a.description as description,a.category_id as category_id, a.image as image, c.name as name, a.user_id as user_id, u.username as username, u.avatar as avatar, w.postalCode as postalCode, w.street as street,w.city as city \n" +
                "FROM ItemSold a\n" +
                "left JOIN CATEGORY c ON a.category_id = c.category_id\n" +
                "left JOIN USERS u ON a.user_id = u.user_id\n" +
                "LEFT JOIN WITHDRAWAL w ON a.article_id = w.article_id " +
                "where c.name = :cat";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("cat", cat);

        return namedParameterJdbcTemplate.query(sql, map, new ItemSoldRowMapper());

    }

    @Override
    public List<ItemSold> findItemsWonByUser(long userId) {

        String sql = "SELECT i.article_id, i.articleName, i.image, i.priceSale, i.auctionEndDate, i.auctionStartDate, " +
                "i.user_id, i.category_id, i.description, i.startingPrice, i.rarity, " +
                "c.name as name, u.username as username " +
                "FROM ItemSold i " +
                "INNER JOIN AUCTION a ON i.article_id = a.article_id " +
                "INNER JOIN CATEGORY c ON i.category_id = c.category_id " +
                "INNER JOIN USERS u ON i.user_id = u.user_id " +
                "WHERE a.user_id = :userId " +
                "AND i.auctionEndDate < GETDATE() " +
                "AND a.auctionAmount = (" +
                "    SELECT MAX(a2.auctionAmount) " +
                "    FROM AUCTION a2 " +
                "    WHERE a2.article_id = i.article_id" +
                ")";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, params, new ItemSoldRowMapper2());
    }

    @Override
    public List<ItemSold> findItemsInProgressByUser(long userId) {

        String sql = "SELECT i.article_id, i.articleName, i.image, i.priceSale, i.auctionEndDate, i.auctionStartDate, " +
                "i.user_id, i.category_id, i.description, i.startingPrice, i.rarity, " +
                "c.name as name, u.username as username " +
                "FROM ItemSold i " +
                "INNER JOIN AUCTION a ON i.article_id = a.article_id " +
                "INNER JOIN CATEGORY c ON i.category_id = c.category_id " +
                "INNER JOIN USERS u ON i.user_id = u.user_id " +
                "WHERE a.user_id = :userId " +
                "AND i.auctionEndDate > GETDATE() ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, params, new ItemSoldRowMapper2());
    }

    @Transactional
    @Override
    public void deleteItemsBySellerId(long sellerId) {
        String sqlWithdrawal = "DELETE w FROM WITHDRAWAL w INNER JOIN ItemSold i ON w.article_id = i.article_id WHERE i.user_id = :sellerId";
        MapSqlParameterSource params = new MapSqlParameterSource("sellerId", sellerId);
        namedParameterJdbcTemplate.update(sqlWithdrawal, params);

        String sqlItems = "DELETE FROM ItemSold WHERE user_id = :sellerId";
        namedParameterJdbcTemplate.update(sqlItems, params);
    }


}
