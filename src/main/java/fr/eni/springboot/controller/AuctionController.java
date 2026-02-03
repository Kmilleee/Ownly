package fr.eni.springboot.controller;

import fr.eni.springboot.bo.Auction;
import fr.eni.springboot.bo.Category;
import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.Rarity;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.*;
import fr.eni.springboot.service.CategoryService;
import org.springframework.core.io.FileSystemResource;
import fr.eni.springboot.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuctionController {

    private final AuctionService service;
    private final ItemSoldService serviceItem;
    private final CategoryService categoryService;
    private final UserService userService;

    public AuctionController(AuctionService service, ItemSoldService serviceItem, UserService userService, CategoryService categoryService) {
        this.service = service;
        this.serviceItem = serviceItem;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/auction")
    public String displayAuction(Model model) {

        List<ItemSold> allItems = serviceItem.readItemSold();
        List<ItemSold> allItemsLegendary = serviceItem.findByRarity(Rarity.LEGENDARY);
        int limit = Math.min(allItems.size(), 8);
        int limit2 = Math.min(allItems.size(), 3);

        List<ItemSold> newCollection = allItemsLegendary.subList(0, limit2);

        List<ItemSold> topAuctions = allItems.subList(0, limit);

        List<ItemSold> otherAuctions = new ArrayList<>();
        if (allItems.size() > 8) {
            otherAuctions = allItems.subList(limit, 12);
        }

        model.addAttribute("newCollection", newCollection);
        model.addAttribute("topAuctions", topAuctions);
        model.addAttribute("otherAuctions", otherAuctions);
        model.addAttribute("userList", serviceItem.readItemsByBetterSel());
        model.addAttribute("activePage", "auction");

        return "auction";
    }

    @GetMapping("/itemsSold-photos/{id}/{imageName}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable("id") String id,
                                               @PathVariable("imageName") String imageName) {

        Path imagePath = Paths.get("itemsSold-photos", id, imageName);
        Resource resource = new FileSystemResource(imagePath);
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok().body(resource);
        } else {
            System.out.println("Image introuvable ici : " + imagePath.toAbsolutePath());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/auctionAll")
    public String displayAuctionAll(Model model) {
        model.addAttribute("activePage", "auction");

        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);


        List<ItemSold> articles = serviceItem.readItemSold();

        ItemSold pub = new ItemSold();
        pub.setArticleName("Mystic Realms");
        pub.setDescription("Découvrez notre nouveau RPG !");
        pub.setImage("pub2.png");
        pub.setStartingPrice(null);

        if (articles.size() >= 3) {
            articles.add(3, pub);
        } else {
            articles.add(pub);
        }


        ItemSold pub2 = new ItemSold();
        pub2.setArticleName("test pub2");
        pub2.setDescription("Découvrez notre nouveau RPG !");
        pub2.setImage("pub.png");
        pub2.setStartingPrice(null);

        if (articles.size() >= 8) {
            articles.add(8, pub2);
        } else {
            articles.add(pub2);
        }

        model.addAttribute("articles", articles);
        return "auctionAll";
    }

    @GetMapping("/auctionDetail")
    public String displayAuctionDetail(@RequestParam("id") long id_item, Model model) {
        model.addAttribute("itemOBJ", serviceItem.readItemById(id_item));

        List<String> listFigurine = new ArrayList<>();
        listFigurine.add("militaireRare.png");
        model.addAttribute("listFigurine", listFigurine);

        List<String> rareCards = new ArrayList<>();
        rareCards.add("militaireCOMMON.png");
        model.addAttribute("rareCards", rareCards);

        List<ItemSold> commonCards = serviceItem.findByRarity(Rarity.COMMON);
        model.addAttribute("commonCards", commonCards);

        List<ItemSold> epicCards = serviceItem.findByRarity(Rarity.EPIC);
        model.addAttribute("commonCards", commonCards);

        List<ItemSold> legendrayCards = serviceItem.findByRarity(Rarity.LEGENDARY);
        model.addAttribute("commonCards", commonCards);

        return "/auctionDetail";
    }

    @PostMapping("/encherir")
    public String placerEnchere(@RequestParam("articleId") long articleId, @RequestParam("montant") int montant, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            String bidderUsername = principal.getName();

            User bidder = userService.readUserByUsername(bidderUsername);
            ItemSold itemSold = serviceItem.readItemById(articleId);

            Auction auction = new Auction();

            auction.setBidder(bidder);
            auction.setItem(itemSold);
            auction.setAuctionDate(LocalDateTime.now());
            auction.setAuctionAmount(montant);

            service.createAuction(auction);

            redirectAttributes.addFlashAttribute("success", "Votre enchère a bien été enregistrée !");


        } catch (RuntimeException e) {
            //RedirectAttributes est nécessaire dans le cas où on redirect (avec le model le redirect l'effacerait car nouvelle requête)
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/auctionDetail?id=" + articleId;
    }

    @GetMapping("/cherche")
    public String displayCherche(@RequestParam(value = "p", required = false) String query, Model model) {

        List<ItemSold> articlesTrouves;

        if (query != null && !query.isEmpty()) {
            articlesTrouves = serviceItem.readItemBySearch(query);
        } else {
            model.addAttribute("activePage", "auction");
            articlesTrouves = serviceItem.readItemSold();


            ItemSold pub = new ItemSold();
            pub.setArticleName("Mystic Realms");
            pub.setDescription("Découvrez notre nouveau RPG !");
            pub.setImage("pub2.png");
            pub.setStartingPrice(null);

            if (articlesTrouves.size() >= 3) {
                articlesTrouves.add(3, pub);
            } else {
                articlesTrouves.add(pub);
            }

            ItemSold pub2 = new ItemSold();
            pub2.setArticleName("test pub2");
            pub2.setDescription("Découvrez notre nouveau RPG !");
            pub2.setImage("pub.png");
            pub2.setStartingPrice(null);

            if (articlesTrouves.size() >= 8) {
                articlesTrouves.add(8, pub2);
            } else {
                articlesTrouves.add(pub2);
            }
        }


        model.addAttribute("articles", articlesTrouves);

        return "auctionAll";
    }

    @GetMapping("/chercheCat")
    public String displayChercheCat(@RequestParam(value = "c", required = false) String cat, Model model) {

        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);


        List<ItemSold> articlesTrouves;

        if (cat != null && !cat.isEmpty()) {
            articlesTrouves = serviceItem.readItemByCategory(cat);
        } else {
            model.addAttribute("activePage", "auction");
            articlesTrouves = serviceItem.readItemSold();


            ItemSold pub = new ItemSold();
            pub.setArticleName("Mystic Realms");
            pub.setDescription("Découvrez notre nouveau RPG !");
            pub.setImage("pub2.png");
            pub.setStartingPrice(null);

            if (articlesTrouves.size() >= 3) {
                articlesTrouves.add(3, pub);
            } else {
                articlesTrouves.add(pub);
            }

            ItemSold pub2 = new ItemSold();
            pub2.setArticleName("test pub2");
            pub2.setDescription("Découvrez notre nouveau RPG !");
            pub2.setImage("pub.png");
            pub2.setStartingPrice(null);

            if (articlesTrouves.size() >= 8) {
                articlesTrouves.add(8, pub2);
            } else {
                articlesTrouves.add(pub2);
            }
        }

        model.addAttribute("articles", articlesTrouves);

        return "auctionAll";
    }

}
