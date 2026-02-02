package fr.eni.springboot.service;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserRepository dao;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {

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
        User currentInDb = dao.readUserById(user.getUser_id());

        if (!user.getPassword().equals(currentInDb.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        dao.updateUser(user);

    }

    @Override
    public void deleteUser(long user_id) {
        dao.deleteUser(user_id );

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
}
