package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Utilisateur;

import java.util.List;

public interface UtilisateurRepository {
    void createUtilisateur(Utilisateur utilisateur);

    List<Utilisateur> readUtilisateur();
}
