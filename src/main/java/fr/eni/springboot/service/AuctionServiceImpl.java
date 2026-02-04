package fr.eni.springboot.service;

import fr.eni.springboot.bo.Auction;
import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.AuctionRepository;
import fr.eni.springboot.repository.ItemSoldRepository;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    AuctionRepository dao;
    ItemSoldRepository itemSoldRepository;
    UserRepository userRepository;

    public AuctionServiceImpl(AuctionRepository dao, ItemSoldRepository itemSoldRepository, UserRepository userRepository) {
        this.dao = dao;
        this.itemSoldRepository = itemSoldRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void createAuction(Auction auction) {
        ItemSold itemEnBase = itemSoldRepository.readItemById(auction.getItem().getId());

        //Vérifie que le montant de l'enchère est plus élevé que celui actuel
        if (auction.getAuctionAmount() <= itemEnBase.getPriceSale()) {
            throw new RuntimeException("L'enchère doit être supérieure au prix actuel !");
        }
        //Vérifie que le user n'enchérie pas sur sa propre vente
        if (itemEnBase.getSeller().getUser_id() == auction.getBidder().getUser_id()) {
            throw new RuntimeException("Vous ne pouvez pas enchérir sur votre propre vente");
        }

        User userBider = userRepository.readUserById(auction.getBidder().getUser_id());

        //Vérifie que le user à assez de crédits pour enchérir
        if (userBider.getCredit() < auction.getAuctionAmount()) {
            throw new RuntimeException("Vous n'avez pas assez de crédits");
        }

        //Récupère l'ID de l'ancien enchère la plus élevée
        Auction ancienneEnchere = dao.findBestAuctionByItemId(auction.getItem().getId());

        //S'il y en a une récupère l'ID du user qui l'a fait
        if (ancienneEnchere != null) {
            User ancienBidder = userRepository.readUserById(ancienneEnchere.getBidder().getUser_id());

            // Rembourse le user
            long nouveauSoldeAncienBidder = ancienBidder.getCredit() + ancienneEnchere.getAuctionAmount();

            //Update ses crédits
            userRepository.updateCredit(ancienBidder.getUser_id(), nouveauSoldeAncienBidder);
        }

        dao.createAuction(auction);

        //Set le montant de l'enchère actuelle au prix de vente de l'item
        itemEnBase.setPriceSale(auction.getAuctionAmount());

        //Mets à jour l'item
        itemSoldRepository.updateItemSold(itemEnBase);

        // Déduit le montant de l'enchère actuelle des crédits du user
        long nouveauSolde = userBider.getCredit() - auction.getAuctionAmount();
        //Mets à jour les crédits du user
        userRepository.updateCredit(userBider.getUser_id(), nouveauSolde);
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

    @Override
    public List<Auction> readItemById(long auction_id) {
        return dao.readItemById(auction_id);
    }
}
