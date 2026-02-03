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
    public ModelAndView handleException(Exception ex){
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
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            user = userService.findByEmail(oauth2User.getAttribute("email"));
        } else {
            user = userService.readUserByUsername(authentication.getName());
        }

        if (user != null) {
            model.addAttribute("UserCo", user);
        }
    }
}
