package fr.eni.springboot.controller;

import fr.eni.springboot.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuctionController {

    private final AuctionService service;

    public AuctionController(AuctionService service) {
        this.service = service;
    }

    @GetMapping("/auction")
    public String displayAuction(Model model){
        return"auction";
    }

    @GetMapping("/auctionAll")
    public String displayAuctionAll(Model model){
        return"auctionAll";
    }

}
