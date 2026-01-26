package fr.eni.springboot.controller;


import fr.eni.springboot.bo.User;
import fr.eni.springboot.bo.Withdrawal;
import fr.eni.springboot.service.WithdrawalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WithdrawalController {

    WithdrawalService service;

    public WithdrawalController(WithdrawalService service) {
        this.service = service;
    }

    @GetMapping("/withdrawal")
    public String displayWithdrawal(Model model){
        model.addAttribute("withdrawalList",service.readWithdrawal());

        return "withdrawal";
    }

    @GetMapping("/addWithdrawal")
    public String displayAddWithdrawal(Model model){

        model.addAttribute("withdrawalOBJ", new Withdrawal());
        return "addWithdrawal";
    }

    @PostMapping("/addWithdrawal")
    public String createWithdrawal(@ModelAttribute("userOBJ") Withdrawal withdrawal){
        service.createWithdrawal(withdrawal);
        service.readWithdrawal().forEach(System.out::println);
        return "redirect:/withdrawal";
    }
}
