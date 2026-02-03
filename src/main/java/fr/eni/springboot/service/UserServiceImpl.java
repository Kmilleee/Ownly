package fr.eni.springboot.service;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository dao;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        User existingUser = dao.readUserByUsername(user.getUsername());
        User existingEmail = dao.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new RuntimeException("Ce pseudo est déjà utilisé par un autre membre.");
        }

        if (existingEmail != null) {
            throw new RuntimeException("Cet Email est déjà lié à un compte.");
        }
        String motDePasseCrypte = passwordEncoder.encode(user.getPassword());
        user.setPassword(motDePasseCrypte);

        dao.createUser(user);
    }

    @Override
    public List<User> readUser() {
        return dao.readUser();
    }

    @Override
    public void updateUser(User user) {
        User userWithSameName = dao.readUserByUsername(user.getUsername());
        User userWithSameEmail = dao.findByEmail(user.getEmail());

        // Verif si le pseudo est déjà utilisé par un autre user
        if (userWithSameName != null && userWithSameName.getUser_id() != user.getUser_id()) {
            throw new RuntimeException("Le pseudo '" + user.getUsername() + "'est déjà pris");
        }

        if (userWithSameEmail != null && userWithSameEmail.getUser_id() != user.getUser_id()) {
            throw new RuntimeException("L'email' '" + user.getEmail() + "'est utilisé par un autre compte");
        }

        User currentInDb = dao.readUserById(user.getUser_id());

        //Si le mdp est différent de celui actuel et n'est pas vide
        if (user.getPassword() != null && !user.getPassword().isEmpty() && !user.getPassword().equals(currentInDb.getPassword())) {
            user.setPassword(currentInDb.getPassword());
        } else {
            //Sinon s'il change on crypte le nouveau mdp
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        dao.updateUser(user);

    }

    @Override
    public void deleteUser(long user_id) {
        dao.deleteUser(user_id);

    }

    @Override
    public User readUserByUsername(String username) {
        return dao.readUserByUsername(username);
    }

    @Override
    public User readUserById(long user_id) {
        return dao.readUserById(user_id);
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);

        dao.updatePassword(email, encodedPassword);
    }

    @Override
    public void disableUser(long id) {
        User user = dao.readUserById(id);

        user.setActive(!user.isActive());

        dao.updateUser(user);
    }

    @Override
    public void updateAvatar(long userId, String imageName) {
        dao.updateAvatar(userId, imageName);
    }

    @Override
    public boolean claimDailyReward(long userId) {
        return dao.claimDailyReward(userId);
    }
}
