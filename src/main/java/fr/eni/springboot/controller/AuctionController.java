package fr.eni.springboot.controller;

import fr.eni.springboot.service.AuctionService;
import fr.eni.springboot.service.ItemSoldService;
import fr.eni.springboot.service.ItemSoldServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        return"auction";
    }

    @GetMapping("/auctionAll")
    public String displayAuctionAll(Model model) {
        model.addAttribute("articles", serviceItem.readItemSold());
        return "auctionAll";
    }

}
