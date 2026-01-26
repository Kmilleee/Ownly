package fr.eni.springboot.service;

import fr.eni.springboot.bo.Auction;

import java.util.List;

public interface AuctionService {
    void createAuction(Auction auction);

    List<Auction> readAuction();

    void updateAuction(Auction auction);

    void deleteAuction(long auction_id);
}
