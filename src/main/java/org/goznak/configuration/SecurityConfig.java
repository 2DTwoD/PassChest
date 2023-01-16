package org.goznak.configuration;

import org.goznak.models.User;
import org.goznak.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.Principal;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    final
    UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(14);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.
                authorizeHttpRequests(request -> request
                                .requestMatchers("/search", "/search/{id}", "/edit/soft/{id}", "/search/history/{id}")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_GUEST")
                                .requestMatchers("/edit", "/search/systems", "/add/system", "edit/system/{id}",
                                        "/search/sub_systems", "/add/sub_system", "edit/sub_system/{id}")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/edit/soft/{id}")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers(HttpMethod.PATCH, "/edit/soft/{id}")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers("/search/users", "/add/user", "edit/user/{username}")
                                .hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "edit/system/{id}", "edit/sub_system/{id}"
                                , "/edit/soft/{id}")
                                .hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                                //.requestMatchers("/**").permitAll().anyRequest().authenticated()
                )
                .formLogin(form -> form.
                        loginPage("/login")
                        .defaultSuccessUrl("/search")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
}
