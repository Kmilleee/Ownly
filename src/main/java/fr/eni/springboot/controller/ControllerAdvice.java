package fr.eni.springboot.controller;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.exception.TestException;
import fr.eni.springboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(TestException.class)
    public ModelAndView handleException(Exception ex){
        //permet de configurer la vue
        ModelAndView model = new ModelAndView();

        //ajoute un statut
        //création d'un statut à moi : 1000
        model.addObject("status", "1000");
        //ajout du message d'erreur
        model.addObject("errorMessage", ex.getMessage());

        //associe le model à la vue que je souhaite affichée
        model.setViewName("error");

        return model;

    }

    private final UserService userService;

    public ControllerAdvice(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute
    public void addUserToModel(Model model, Authentication authentication) {

        if (authentication == null) {
            return;
        }

        User user = null;

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");
            user = userService.findByEmail(email);
        } else {
            String username = authentication.getName();
            user = userService.readUserByUsername(username);
        }

        if (user != null) {
            model.addAttribute("UserCo", user);
        }
    }

}
