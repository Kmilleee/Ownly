package fr.eni.springboot.bo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ItemSold {
    private long id;
    private String articleName;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime auctionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime auctionEndDate;
    private Long startingPrice;
    private Long priceSale;
    private String image;
    private Rarity rarity;
    private User buyer;
    private User seller;
    private Category category;
    private Withdrawal withdrawal;
    private StatusSale status;

    public ItemSold(long id, String articleName, String description, LocalDateTime auctionStartDate, LocalDateTime auctionEndDate, Long startingPrice, Long priceSale, String image, Rarity rarity, User buyer, User seller, Category category, Withdrawal withdrawal, StatusSale status) {
        this.id = id;
        this.articleName = articleName;
        this.description = description;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.startingPrice = startingPrice;
        this.priceSale = priceSale;
        this.image = image;
        this.rarity = rarity;
        this.buyer = buyer;
        this.seller = seller;
        this.category = category;
        this.withdrawal = withdrawal;
        this.status = status;
    }

    public ItemSold(String articleName, String description, LocalDateTime auctionStartDate, LocalDateTime auctionEndDate, Long startingPrice, Long priceSale, String image, Rarity rarity, User buyer, User seller, Category category, Withdrawal withdrawal, StatusSale status) {
        this.articleName = articleName;
        this.description = description;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.startingPrice = startingPrice;
        this.priceSale = priceSale;
        this.image = image;
        this.rarity = rarity;
        this.buyer = buyer;
        this.seller = seller;
        this.category = category;
        this.withdrawal = withdrawal;
        this.status = status;
    }

    public StatusSale getStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (this.auctionStartDate.isAfter(now)) {
            return StatusSale.NOT_STARTED;
        }
        else if (this.auctionEndDate.isBefore(now)) {
            return StatusSale.COMPLETED;
        }
        else {
            return StatusSale.IN_PROGRESS;
        }
    }

    public void setStatus(StatusSale status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(LocalDateTime auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }

    public LocalDateTime getAuctionEndDate() {
        return auctionEndDate;
    }

    public void setAuctionEndDate(LocalDateTime auctionEndDate) {
        this.auctionEndDate = auctionEndDate;
    }

    public Long getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Long startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Long getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Long priceSale) {
        this.priceSale = priceSale;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Withdrawal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Withdrawal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public ItemSold() {
    }


}
