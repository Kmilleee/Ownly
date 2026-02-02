package fr.eni.springboot.controller;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.service.AuctionService;
import fr.eni.springboot.service.ItemSoldService;
import fr.eni.springboot.service.ItemSoldServiceImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AuctionController {

    private final AuctionService service;
    private final ItemSoldService serviceItem;

    public AuctionController(AuctionService service, ItemSoldService serviceItem) {
        this.service = service;
        this.serviceItem = serviceItem;
    }

    @GetMapping("/auction")
    public String displayAuction(Model model){
        model.addAttribute("activePage", "auction");
        return"auction";
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
    public String displayAuctionDetail(@RequestParam("id") long  id_item, Model model) {
        model.addAttribute("itemOBJ", serviceItem.readItemById(id_item));

        return "/auctionDetail";
    }

}
