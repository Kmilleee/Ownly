package fr.eni.springboot.repository;

import fr.eni.springboot.bo.User;

import java.util.List;

public interface UserRepository {

    void createUser(User user);

    List<User> readUser();

    User readUserById(long user_id);

    void updateUser(User user);

    void deleteUser(long user_id);

    User readUserByUsername(String username);

    User findByEmail(String email);

    void updatePassword(String email, String encodedPassword);

    void updateAvatar(long userId, String imageName);

    boolean claimDailyReward(long userId);
}
