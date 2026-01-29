package fr.eni.springboot.controller;


import fr.eni.springboot.bo.Category;
import fr.eni.springboot.bo.Withdrawal;
import fr.eni.springboot.service.WithdrawalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WithdrawalController {

    WithdrawalService service;

    public WithdrawalController(WithdrawalService service) {
        this.service = service;
    }

    @GetMapping("/withdrawal")
    public String displayWithdrawal(Model model){
        model.addAttribute("withdrawalList",service.readWithdrawal());
        model.addAttribute("activePage", "withdrawal");

        return "withdrawal";
    }

    @GetMapping("/admin/addWithdrawal")
    public String displayAddWithdrawal(Model model){

        model.addAttribute("withdrawalOBJ", new Withdrawal());
        return "admin/addWithdrawal";
    }

    @PostMapping("/admin/addWithdrawal")
    public String createWithdrawal(@ModelAttribute("userOBJ") Withdrawal withdrawal){
        service.createWithdrawal(withdrawal);
        service.readWithdrawal().forEach(System.out::println);
        return "redirect:/withdrawal";
    }

    @DeleteMapping("/admin/deleteWithdrawal")
    public String deleteWithdrawal(@RequestParam("id") long id) {
        service.deleteWithdrawal(id);
        return "redirect:/withdrawal";
    }

    @PostMapping("/admin/updateWithdrawal")
    public String updateWithdrawal(@ModelAttribute(name = "withdrawal") Withdrawal withdrawal) {
        service.updateWithdrawal(withdrawal);
        return "redirect:/withdrawal";
    }

}
