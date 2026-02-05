package fr.eni.springboot.controller;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.ItemSoldService;
import fr.eni.springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ItemSoldService itemSoldService;

    public UserController(UserService userService, ItemSoldService itemSoldService) {
        this.userService = userService;
        this.itemSoldService = itemSoldService;
    }

    @GetMapping("/")
    public String displayIndex(Model model) {

        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/about")
    public String displayAbout(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/user")
    public String displayUser(Model model) {
        model.addAttribute("userList", userService.readUser());

        return "user";
    }

    @GetMapping("/admin/addUser")
    public String displayAdduser(Model model) {

        model.addAttribute("userOBJ", new User());
        return "admin/addUser";
    }

    @PostMapping("/admin/addUser")
    public String createUser(@ModelAttribute("userOBJ") User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/user";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/addUser";
        }
    }

    @GetMapping("/admin/admin")
    public String displayAdmin(Model model) {
        model.addAttribute("activePage", "admin");
        // model.addAttribute("NameUser", userService.readUserById());
        return "admin/admin";
    }


    @PostMapping("/signup")
    public String createUserInscription(@ModelAttribute("userOBJ") User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/login?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "/signup";
        }
    }


    @GetMapping("/signup")
    public String displaySignup(Model model) {
        model.addAttribute("userOBJ", new User());
        return "/signup";
    }

    @GetMapping("/profile")
    public String displayProfile(Principal principal, Model model, Authentication authentication) {
        model.addAttribute("activePage", "profile");

        User user = null;
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            user = userService.findByEmail(email);
        } else if (principal != null) {
            user = userService.readUserByUsername(principal.getName());
        }

        if (user == null) {
            user = new User();
            user.setUsername("Utilisateur Google");
            user.setAvatar("default.png");
            user.setFirstName("Compte");
            user.setLastName("Invité");
        }
        if (user.getUser_id() != 0) {
            model.addAttribute("mesAchats", itemSoldService.findItemsWonByUser(user.getUser_id()));
            model.addAttribute("mesAchatsEnCours", itemSoldService.findItemsInProgressByUser(user.getUser_id()));
            model.addAttribute("mesVentes", itemSoldService.readItemsBySeller(user.getUser_id()));

            // Gestion de la récompense
            boolean isAvailable = true;
            if (user.getLastDailyReward() != null && user.getLastDailyReward().equals(LocalDate.now())) {
                isAvailable = false;
            }
            model.addAttribute("isRewardAvailable", isAvailable);
        } else {
            model.addAttribute("mesAchats", new ArrayList<>());
            model.addAttribute("mesAchatsEnCours", new ArrayList<>());
            model.addAttribute("mesVentes", new ArrayList<>());
            model.addAttribute("isRewardAvailable", false);
        }

        model.addAttribute("UserCo", user);
        return "/profile";
    }


    @GetMapping("/profileOther")
    public String displayProfileOther(@RequestParam("id")long user_id, Model model, Authentication authentication) {
        model.addAttribute("activePage", "profile");
        model.addAttribute("mesAchats", itemSoldService.findItemsWonByUser(user_id));
        model.addAttribute("mesAchatsEnCours", itemSoldService.findItemsInProgressByUser(user_id));

        userService.readUserById(user_id);

        User user = userService.readUserById(user_id);



        List<ItemSold> mesVentes = new ArrayList<>();
        mesVentes = itemSoldService.readItemsBySeller(user.getUser_id());
        model.addAttribute("mesVentes", mesVentes);
        model.addAttribute("User", userService.readUserById(user_id));
        return "/profileOther";
    }


    @GetMapping("/changeProfile")
    public String displayChangeProfile(Principal principal, Model model, Authentication authentication) {
        String username = principal.getName();

        User user = userService.readUserByUsername(username);
        if (user == null) {
            user = new User();
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                user.setUsername(oauth2User.getAttribute("name"));
                user.setEmail(oauth2User.getAttribute("email"));
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("UserOBJ", user);
        model.addAttribute("UserCo", user);
        return "/changeProfile";
    }

    @PostMapping("/changeProfile")
    public String displayUpdateProfile(@ModelAttribute("UserOBJ") User formUser, Model model, Authentication authentication) {
        try {
            User existingUser = userService.readUserByUsername(authentication.getName());

            if (existingUser != null) {
                formUser.setUser_id(existingUser.getUser_id());

                userService.updateUser(formUser);

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        formUser.getUsername(),
                        authentication.getCredentials(),
                        authentication.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                return "redirect:/profile";
            }
            return "redirect:/";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("UserOBJ", formUser);
            model.addAttribute("UserCo", userService.readUserByUsername(authentication.getName()));
            return "/changeProfile";
        }
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long id, HttpServletRequest request) {
        userService.deleteUser(id);

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @PostMapping("/disable-user")
    public String disableUser(@RequestParam("id") long id) {
        userService.disableUser(id);
        return "redirect:/user";
    }

    @GetMapping("/changeProfile/edit-avatar")
    public String afficherSelectionAvatar(Model model) {
        List<String> avatars = Arrays.asList(
                "clown.png",
                "coureur.png",
                "dormeur.png",
                "militaire.png",
                "peintre.png"
        );


        model.addAttribute("avatarList", avatars);

        return "/edit-avatar";
    }

    @PostMapping("/changeProfile/edit-avatar")
    public String enregistrerAvatar(@RequestParam("avatarChoice") String avatarName, Principal principal) {
        User userConnecte = userService.readUserByUsername(principal.getName());
        if (userConnecte != null) {
            userService.updateAvatar(userConnecte.getUser_id(), avatarName);
        }

        return "redirect:/profile";
    }


    @GetMapping("/collection")
    public String displayCollection(){
        return "/collection";

    }




}
