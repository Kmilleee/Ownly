package fr.eni.springboot.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.nio.file.Paths;

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

        model.addAttribute("articles", serviceItem.readItemSold());
        return "auctionAll";
    }

}
