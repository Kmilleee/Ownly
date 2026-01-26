package fr.eni.springboot.controller;

import fr.eni.springboot.repository.exception.TestException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

}
