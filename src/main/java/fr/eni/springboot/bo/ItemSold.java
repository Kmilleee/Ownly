package fr.eni.springboot.bo;

import java.time.LocalDate;

public class ItemSold {

    private long id;
    private String articleName;
    private String description;
    private LocalDate auctionStartDate;
    private LocalDate auctionEndDate;
    private long startingPrice;
    private long priceSale;

    private User buyer;
    private User seller;
    private Category category;
    private Withdrawal withdrawal;

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

    public LocalDate getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(LocalDate auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }

    public LocalDate getAuctionEndDate() {
        return auctionEndDate;
    }

    public void setAuctionEndDate(LocalDate auctionEndDate) {
        this.auctionEndDate = auctionEndDate;
    }

    public long getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(long startingPrice) {
        this.startingPrice = startingPrice;
    }

    public long getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(long priceSale) {
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

    public ItemSold() {
    }

    public ItemSold(long id, String articleName, String description, LocalDate auctionStartDate, LocalDate auctionEndDate, long startingPrice, long priceSale, User buyer, User seller, Category category, Withdrawal withdrawal) {
        this.id = id;
        this.articleName = articleName;
        this.description = description;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.startingPrice = startingPrice;
        this.priceSale = priceSale;
        this.buyer = buyer;
        this.seller = seller;
        this.category = category;
        this.withdrawal = withdrawal;
    }

    public ItemSold(String articleName, String description, LocalDate auctionStartDate, LocalDate auctionEndDate, long startingPrice, long priceSale, User buyer, User seller, Category category, Withdrawal withdrawal) {
        this.articleName = articleName;
        this.description = description;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.startingPrice = startingPrice;
        this.priceSale = priceSale;
        this.buyer = buyer;
        this.seller = seller;
        this.category = category;
        this.withdrawal = withdrawal;
    }
}
