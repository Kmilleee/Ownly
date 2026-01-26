package fr.eni.springboot.service;

import fr.eni.springboot.bo.User;

import java.util.List;

public interface UserService {

    void createUser(User user);

    List<User> readUser();

    void updateUser(User user);

    void deleteUser(long user_id);

    User readUserByUsername(String username);
}
