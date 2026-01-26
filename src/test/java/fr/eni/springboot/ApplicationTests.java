package fr.eni.springboot;

import fr.eni.springboot.bo.User; // Vérifiez votre import ici
import fr.eni.springboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Import statique pour avoir des assertions lisibles
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // Annule les modifs à la fin de chaque test (Rollback)
class ApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void displayUser() {

        List<User> users = userRepository.readUser();
        System.out.println("Nombre d'utilisateurs trouvés : " + users.size());

        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(4);
        assertThat(users).extracting("username").contains("annelise");
    }

    @Test
    void createUser() {
        // 1. ARRANGE : On prépare un nouvel utilisateur
        User nouveau = new User();
        nouveau.setUsername("TestMan");
        nouveau.setLastName("Test");
        nouveau.setFirstName("Unit");
        nouveau.setEmail("test@unit.fr");
        nouveau.setStreet("Rue du Bug");
        nouveau.setPostalCode("00000");
        nouveau.setCity("ComputerCity");
        nouveau.setPassword("123456");
        nouveau.setCredit(100L);
        nouveau.setAdmin(false);
        nouveau.setActive(true);


        userRepository.createUser(nouveau);

        assertThat(nouveau.getUser_id()).isNotNull();
        System.out.println("Nouvel ID généré : " + nouveau.getUser_id());
        List<User> users = userRepository.readUser();
        assertThat(users).extracting("username").contains("TestMan");
        assertThat(users).hasSize(5);
    }
}