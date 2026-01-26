package fr.eni.springboot.bo;

import org.apache.catalina.User;

import java.time.LocalDate;

public class Auction {


private long auction_id;
    private LocalDate auctionDate;
    private long auctionAmount;

    private ItemSold item;
    private User bidder;

    public long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(long auction_id) {
        this.auction_id = auction_id;
    }

    public LocalDate getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(LocalDate auctionDate) {
        this.auctionDate = auctionDate;
    }

    public long getAuctionAmount() {
        return auctionAmount;
    }

    public void setAuctionAmount(long auctionAmount) {
        this.auctionAmount = auctionAmount;
    }

    public ItemSold getItem() {
        return item;
    }

    public void setItem(ItemSold item) {
        this.item = item;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Auction() {
    }

    public Auction(long auction_id, LocalDate auctionDate, long auctionAmount, ItemSold item, User bidder) {
        this.auction_id = auction_id;
        this.auctionDate = auctionDate;
        this.auctionAmount = auctionAmount;
        this.item = item;
        this.bidder = bidder;
    }

    public Auction(LocalDate auctionDate, long auctionAmount, ItemSold item, User bidder) {
        this.auctionDate = auctionDate;
        this.auctionAmount = auctionAmount;
        this.item = item;
        this.bidder = bidder;
    }
}
