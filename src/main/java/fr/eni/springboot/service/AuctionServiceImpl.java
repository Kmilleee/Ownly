package fr.eni.springboot.service;

import fr.eni.springboot.bo.Auction;
import fr.eni.springboot.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService{

    AuctionRepository dao;

    public AuctionServiceImpl(AuctionRepository dao) {
        this.dao = dao;
    }

    @Override
    public void createAuction(Auction auction) {
        dao.createAuction(auction);

    }

    @Override
    public List<Auction> readAuction() {
        return dao.readAuction();
    }

    @Override
    public void updateAuction(Auction auction) {
        dao.updateAuction(auction);

    }

    @Override
    public void deleteAuction(long auction_id) {
        dao.deleteAuction(auction_id);

    }
}
