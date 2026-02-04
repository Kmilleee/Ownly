package fr.eni.springboot.controller;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class CreditController {

    private final UserService userService;

    public CreditController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/creditReward")
    public String getDailyReward(Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.readUserByUsername(principal.getName());

        if (user != null) {
            boolean success = userService.claimDailyReward(user.getUser_id());

            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", " Bravo ! Tu as reçu tes 500 crédits quotidiens.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Tu as déjà récupéré ton cadeau aujourd'hui. Reviens demain !");
            }
        }

        return "redirect:/creditReward";
    }

    @GetMapping("/creditReward")
    public String showBank(Model model, Principal principal) {
        User user = userService.readUserByUsername(principal.getName());

        boolean isAvailable = true;
        if (user.getLastDailyReward() != null) {
            if (user.getLastDailyReward().equals(LocalDate.now())) {
                isAvailable = false;
            }
        }

        model.addAttribute("isRewardAvailable", isAvailable);
        return "creditReward";
    }
}
