package fr.eni.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {
    //configuration de l'utilisation de la base de donnée pour se connecter
// Rien à changer
    @Bean
    UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // 1. Requête pour récupérer l'utilisateur (username, password, enabled)
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                // Pour l'object principal : username en premier donc avec principal.getName ça retourne username
                "SELECT username, password, active FROM USERS WHERE username = ?"
        );

        // 2. Requête pour récupérer les rôles
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.role FROM USERS u JOIN ROLES r ON u.user_id = r.user_id WHERE u.username = ?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // mise en place de la gestion des droits en fonction des pages affichées
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //c'est ici que l'on va authoriser les chemins en fonction des utilisateurs
        http.authorizeHttpRequests(auth -> {
            //authoriser l'accès à la liste des glaces aux employés
            // accès au chemin /icecream en Get pour les employé
            auth.
                    /* les changments c'est ici le reste ne change pas il n'y a pas de raison
                     *           *********************************************    */

                            requestMatchers(HttpMethod.GET, "/withdrawal").hasRole("USER")
                    /*accès au chemin /icecream/add en Get pour les admins */
                    .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers(HttpMethod.GET, "/about").permitAll()
                    .requestMatchers(HttpMethod.GET, "/admin/addWithdrawal").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/addUser").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/ventes/").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/auction").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/auctionAll").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/ventes/createSale").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/addWithdrawal").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/addUser").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/admin").hasRole("ADMIN")
                    .requestMatchers("/.well-know/**").permitAll()

                    /* *********************************************                     jusqu'à là */
                    //donne à tous la permission sur la page d'accueil
                    .requestMatchers("/*").permitAll()
                    //donner acces au css
                    .requestMatchers("/css/*").permitAll()
                    //donner acces au image
                    .requestMatchers("/img/*").permitAll()
                    .requestMatchers("/js/**").permitAll()

                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/ventes/**").hasRole("ADMIN")

                    .requestMatchers("/ventes/createSale").hasRole("ADMIN")
                    .requestMatchers("/admin/addUser").hasRole("ADMIN")
                    .requestMatchers("/admin/addWithdrawal").hasRole("ADMIN")
                    .requestMatchers("/admin/admin").hasRole("ADMIN")


                    //tous ce qui n'est pas spécifié n'est pas accessible
                    .anyRequest().denyAll();
        });
        //gestion automatique du login
        http.formLogin(Customizer.withDefaults());


        /*** pas touche *****/
        //gestion du login
        http.formLogin(form -> {
                    //donne l'accès à la page de login à tous
                    form.loginPage("/login").permitAll();
                    //redirige après le login sur la page d'accueil
                    form.defaultSuccessUrl("/", true);
                }
        );

        http.logout(logout -> {
            //déterminer la page à utiliser pour le logout
            logout.logoutUrl("/logout")
                    //redirige après le logout sur la page d'accueil
                    .logoutSuccessUrl("/");
        });


        return http.build();





    }


}