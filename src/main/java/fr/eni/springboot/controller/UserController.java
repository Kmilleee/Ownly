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
    public String createUser(@ModelAttribute("userOBJ") User user) {
        userService.createUser(user);
        userService.readUser().forEach(System.out::println);
        return "redirect:/user";
    }

    @GetMapping("/admin/admin")
    public String displayAdmin(Model model) {
        model.addAttribute("activePage", "admin");
        // model.addAttribute("NameUser", userService.readUserById());
        return "admin/admin";
    }


    @PostMapping("/singup")
    public String createUserInscription(@ModelAttribute("userOBJ") User user) {
        System.out.println("Attempting to create user: " + user.getUsername());
        userService.createUser(user);
        return "redirect:/login";
    }


    @GetMapping("/singup")
    public String displaySignup(Model model) {
        model.addAttribute("userOBJ", new User());
        return "/singup";
    }

    @GetMapping("/profile")
    public String displayProfile(Principal principal, Model model, Authentication authentication) {
        model.addAttribute("activePage", "profile");

        User user = null;
        List<ItemSold> mesVentes = new ArrayList<>();

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");
            user = userService.findByEmail(email);
        } else {
            user = userService.readUserByUsername(principal.getName());
        }
        if (user != null) {
            mesVentes = itemSoldService.readItemsBySeller(user.getUser_id());
        } else {
            user = new User();
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                user.setUsername(oauth2User.getAttribute("name"));
                user.setEmail(oauth2User.getAttribute("email"));
                user.setFirstName("Compte Google");
            } else {
                user.setUsername(principal.getName());
            }
        }

        model.addAttribute("mesVentes", mesVentes);
        model.addAttribute("UserCo", user);
        return "/profile";
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
    public String displayUpdateProfile(@ModelAttribute("UserOBJ") User formUser, Model model, Principal principal, Authentication authentication) {

        User existingUser = null;

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");
            existingUser = userService.findByEmail(email);
        } else {
            existingUser = userService.readUserByUsername(authentication.getName());
        }


        if (existingUser == null) {

            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                formUser.setEmail(oauth2User.getAttribute("email"));
            }
            userService.createUser(formUser);
        } else {
            formUser.setUser_id(existingUser.getUser_id());
            formUser.setEmail(existingUser.getEmail());

            userService.updateUser(formUser);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                formUser.getUsername(),
                auth.getCredentials(),
                auth.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        System.out.println("✅ Profil mis à jour avec succès !");
        return "redirect:/profile";
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

        System.out.println("Nouvel avatar choisi : " + avatarName);
        return "redirect:/profile";
    }


}
