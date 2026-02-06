package fr.eni.springboot.service;

import fr.eni.springboot.bo.Auction;
import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.StatusSale;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.AuctionRepository;
import fr.eni.springboot.repository.ItemSoldRepository;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository dao;
    AuctionRepository auctionRepository;
    ItemSoldRepository itemSoldRepository;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository dao, AuctionRepository auctionRepository, ItemSoldRepository itemSoldRepository, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.auctionRepository = auctionRepository;
        this.itemSoldRepository = itemSoldRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        User existingUser = dao.readUserByUsername(user.getUsername());
        User existingEmail = dao.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new RuntimeException("Ce pseudo est déjà utilisé par un autre membre.");
        }

        if (existingEmail != null) {
            throw new RuntimeException("Cet Email est déjà lié à un compte.");
        }
        String motDePasseCrypte = passwordEncoder.encode(user.getPassword());
        user.setPassword(motDePasseCrypte);

        dao.createUser(user);
    }

    @Override
    public List<User> readUser() {
        return dao.readUser();
    }

    @Override
    public void updateUser(User user) {
        User userWithSameName = dao.readUserByUsername(user.getUsername());
        User userWithSameEmail = dao.findByEmail(user.getEmail());

        // Verif si le pseudo est déjà utilisé par un autre user
        if (userWithSameName != null && userWithSameName.getUser_id() != user.getUser_id()) {
            throw new RuntimeException("Le pseudo '" + user.getUsername() + "'est déjà pris");
        }

        if (userWithSameEmail != null && userWithSameEmail.getUser_id() != user.getUser_id()) {
            throw new RuntimeException("L'email' '" + user.getEmail() + "'est utilisé par un autre compte");
        }

        User currentInDb = dao.readUserById(user.getUser_id());

        //Si le mdp est différent de celui actuel et n'est pas vide
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(currentInDb.getPassword());
        } else {
            //Sinon s'il change on crypte le nouveau mdp
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        dao.updateUser(user);

    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        // 1. Récupérer les ventes de l'utilisateur
        List<ItemSold> items = itemSoldRepository.readItemsBySeller(id);

        for (ItemSold item : items) {
            if (item.getStatus() == StatusSale.IN_PROGRESS) {
                Auction bestAuction = auctionRepository.findBestAuctionByItemId(item.getId());
                if (bestAuction != null) {
                    User bidder = bestAuction.getBidder();
                    bidder.setCredit(bidder.getCredit() + bestAuction.getAuctionAmount());
                    dao.updateUser(bidder);
                }
            }

            auctionRepository.deleteAuctionsByItemId(item.getId());        }

        auctionRepository.deleteAuctionsByUserId(id);

        itemSoldRepository.deleteItemsBySellerId(id);

        dao.deleteUser(id);
    }

    @Override
    public User readUserByUsername(String username) {
        return dao.readUserByUsername(username);
    }

    @Override
    public User readUserById(long user_id) {
        return dao.readUserById(user_id);
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);

        dao.updatePassword(email, encodedPassword);
    }

    @Transactional
    @Override
    public void disableUser(long id) {
        User user = dao.readUserById(id);
        if (user.isActive()) {
            //Récupère ses ventes en cours
            List<ItemSold> itemSoldList = itemSoldRepository.readItemsBySeller(id);

            //Pour toutes les ventes on récup le user qui a la meilleure enchère
            for (ItemSold itemSold : itemSoldList) {
                if (itemSold.getStatus() == StatusSale.IN_PROGRESS) {
                    Auction bestAuction = auctionRepository.findBestAuctionByItemId(itemSold.getId());

                    if (bestAuction != null) {
                        // On le rembourse
                        User bidder = bestAuction.getBidder();
                        bidder.setCredit(bidder.getCredit() + bestAuction.getAuctionAmount());
                        dao.updateUser(bidder);
                    }

                }
            }

            //Et après on supprime (ventes et enchères)
            auctionRepository.deleteAuctionsByUserId(id);
            auctionRepository.deleteAuctionsBySellerId(id);

            itemSoldRepository.deleteItemsBySellerId(id);
        }

        user.setActive(!user.isActive());
        dao.updateUser(user);
    }

    @Override
    public void updateAvatar(long userId, String imageName) {
        dao.updateAvatar(userId, imageName);
    }

    @Override
    public boolean claimDailyReward(long userId) {
        return dao.claimDailyReward(userId);
    }
}
