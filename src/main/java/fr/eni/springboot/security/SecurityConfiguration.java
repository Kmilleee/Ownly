package fr.eni.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    //configuration de l'utilisation de la base de donnée pour se connecter
// Rien à changer
    @Bean
    UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Récupère l'utilisateur, son mot de passe et s'il est actif
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, active FROM USERS WHERE username = ?"
        );

        // Récupère le rôle : Si admin=1 -> ROLE_ADMIN, Sinon -> ROLE_USER
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, CASE WHEN admin = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_USER' END FROM USERS WHERE username = ?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    // mise en place de la gestion des droits en fonction des pages affichées
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //c'est ici que l'on va authoriser les chemins en fonction des utilisateurs
        http.sessionManagement(session -> session
                        .invalidSessionUrl("/login?expired=true")
                        .sessionFixation(fixation -> fixation.migrateSession())
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                )
                .authorizeHttpRequests(auth -> {

                    //authoriser l'accès à la liste des glaces aux employés
                    // accès au chemin /icecream en Get pour les employé
                    auth.
                            /* les changments c'est ici le reste ne change pas il n'y a pas de raison
                             *           *********************************************    */

                                    requestMatchers(HttpMethod.GET, "/withdrawal").hasRole("USER")

                            /*accès au chemin /icecream/add en Get pour les admins */
                            .requestMatchers(HttpMethod.POST, "/deleteUser").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/deleteUser").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/").permitAll()
                            .requestMatchers(HttpMethod.GET, "/about").permitAll()
                            .requestMatchers(HttpMethod.GET, "/cherche").permitAll()
                            .requestMatchers(HttpMethod.GET, "/admin/addWithdrawal").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/admin/addUser").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/ventes/").authenticated()
                            .requestMatchers(HttpMethod.GET, "/auction").permitAll()
                            .requestMatchers(HttpMethod.GET, "/forgotPassword").permitAll()
                            .requestMatchers(HttpMethod.POST, "/changeProfile/edit-avatar").permitAll()
                            .requestMatchers(HttpMethod.GET, "/edit-avatar").permitAll()
                            .requestMatchers(HttpMethod.GET, "/auctionAll").permitAll()
                            .requestMatchers(HttpMethod.GET, "/ventes/createSale").authenticated()
                            .requestMatchers(HttpMethod.GET, "/auctionDetail").authenticated()
                            .requestMatchers(HttpMethod.POST, "/admin/addWithdrawal").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/admin/addUser").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/ventes/createSale").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/admin/admin").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/admin/categoryList").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/admin/createCat").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/admin/admin").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/deleteUser").authenticated()
                            .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                            .requestMatchers(HttpMethod.GET, "/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/creditReward").permitAll()
                            .requestMatchers(HttpMethod.POST, "/creditReward").permitAll()
                            .requestMatchers(HttpMethod.GET, "/ventes/edit-sale").authenticated()
                            .requestMatchers(HttpMethod.POST, "/ventes/edit-sale").authenticated()
                            .requestMatchers(HttpMethod.POST, "/ventes/delete-sale").permitAll()
                            .requestMatchers("/.well-know/**").permitAll()


                    /* *********************************************                     jusqu'à là */
                    //donne à tous la permission sur la page d'accueil
                    .requestMatchers("/*").permitAll()
                    //donner acces au css
                    .requestMatchers("/css/*").permitAll()
                    //donner acces au image
                    .requestMatchers("/img/*").permitAll()
                            .requestMatchers("/img/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .requestMatchers("/js/**").permitAll()
                    .requestMatchers("/font/**").permitAll()
                    .requestMatchers("/itemsSold-photos/**").permitAll()
                            .requestMatchers("/changeProfile/**").authenticated()
                            .requestMatchers("/changeProfile/**").authenticated()


                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/ventes/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/chercheCat").permitAll()
                    .requestMatchers("/ventes/createSale").hasRole("ADMIN")
                    .requestMatchers("/admin/addUser").hasRole("ADMIN")
                    .requestMatchers("/admin/addWithdrawal").hasRole("ADMIN")
                    .requestMatchers("/admin/admin").hasRole("ADMIN")
                    .requestMatchers("/admin/createCat").hasRole("ADMIN")
                    .requestMatchers("/admin/categoryList").hasRole("ADMIN")


                    //tous ce qui n'est pas spécifié n'est pas accessible
                    .anyRequest().denyAll();
        });
        //gestion automatique du login
        http.formLogin(withDefaults());


        /*** pas touche *****/
        //gestion du login
        http.formLogin(form -> {
                    //donne l'accès à la page de login à tous
                    form.loginPage("/login").permitAll();
                    //redirige après le login sur la page d'accueil
                    form.defaultSuccessUrl("/", true);
                }
        );

        http.oauth2Login((oauth2) -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/changeProfile", true)
        );

        http.logout(logout -> {
            //déterminer la page à utiliser pour le logout
            logout.logoutUrl("/logout")
                    .deleteCookies("JSESSIONID", "remember-me")
                    //redirige après le logout sur la page d'accueil
                    .logoutSuccessUrl("/");
        });


        return http
                .rememberMe(rememberMe -> rememberMe
                        .key("maCleSecreteUnique") // Une chaîne de caractères unique au projet
                        .tokenValiditySeconds(86400) // Durée du cookie (ici 24h)
                        .rememberMeParameter("remember-me")
                )
                .build();


    }


}