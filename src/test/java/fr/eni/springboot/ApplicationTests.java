package fr.eni.springboot;

import fr.eni.springboot.repository.UserRepository;
import fr.eni.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    UserRepository userRepository;


    @Test
    void displayUser() {
        userRepository.readUser();
        userRepository.readUser().forEach(System.out::println);
    }

    @Test
    void createUser() {
        userRepository.readUser();
        userRepository.readUser().forEach(System.out::println);
    }

}
