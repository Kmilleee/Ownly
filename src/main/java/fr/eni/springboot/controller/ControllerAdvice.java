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

    private final UserService userService;

    public ControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(TestException.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView model = new ModelAndView();
        model.addObject("status", "1000");
        model.addObject("errorMessage", ex.getMessage());
        model.setViewName("error");
        return model;
    }

    @ModelAttribute
    public void addUserToModel(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return;

        User user = null;
        if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            user = userService.findByEmail(email);
            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setUsername(oauth2User.getAttribute("name"));
                user.setFirstName(oauth2User.getAttribute("given_name"));
                user.setLastName(oauth2User.getAttribute("family_name"));
                user.setAvatar("default.png");
                user.setPassword("OAUTH2_USER");

                user.setStreet("À renseigner");
                user.setCity("À renseigner");
                user.setPostalCode("00000");
                user.setNumPhone("0000000000");
                user.setCredit(0);
                user.setAdmin(false);
                user.setActive(true);
                userService.createUser(user);
                user = userService.findByEmail(email);
            }
        } else {
            user = userService.readUserByUsername(authentication.getName());
        }

        model.addAttribute("UserCo", user);
    }
}
