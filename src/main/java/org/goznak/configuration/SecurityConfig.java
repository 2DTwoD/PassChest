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
                                .requestMatchers(HttpMethod.DELETE)
                                .hasRole("ADMIN")

                                .requestMatchers("/search/users", "/add/user", "/edit/user/*")
                                .hasRole("ADMIN")

                                .requestMatchers("/edit", "/search/systems", "/add/system", "/edit/system/*",
                                        "/search/sub_systems", "/add/sub_system", "/edit/sub_system/*",
                                        "/edit/soft/*", "/add/soft/*", "/search/*")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.PATCH)
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers("/search", "/search/*", "/search/history/*", "/get_pass/*", "/css/**", "/js/**")
                                .hasAnyRole("ADMIN", "USER", "GUEST")

                                .requestMatchers("/").permitAll()
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
