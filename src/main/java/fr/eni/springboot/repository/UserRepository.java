package fr.eni.springboot.repository;

import fr.eni.springboot.bo.User;

import java.util.List;

public interface UserRepository {

    void createUser(User user);

    List<User> readUser();

    void updateUser();
}
