package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Auction;

import java.util.List;

public interface AuctionRepository {
    void createAuction(Auction auction);

    List<Auction> readAuction();

    void updateAuction(Auction auction);

    void deleteAuction(long auction_id);

    Auction findBestAuctionByItemId(long itemId);

    List<Auction> readItemById(long auction_id);

    void deleteAuctionsByUserId(long userId);

    void deleteAuctionsBySellerId(long sellerId);
}
