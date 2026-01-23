package fr.eni.springboot.service;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserRepository dao;

    public UserServiceImpl(UserRepository dao) {
        this.dao = dao;
    }


    @Override
    public void createUser(User user) {
        dao.createUser(user);
    }

    @Override
    public List<User> readUser() {
        return dao.readUser();
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);

    }

    @Override
    public void deleteUser(long user_id) {
        dao.deleteUser(user_id );

    }
}
