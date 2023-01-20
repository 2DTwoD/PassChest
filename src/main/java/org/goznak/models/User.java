package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.goznak.PassChestApplication;
import org.goznak.services.UserService;
import org.goznak.utils.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "users")
@Component
public class User implements Comparable<User>{
    @Id
    @Size(min = 3, max = 30, message = "Имя пользователя должно содержать от 3 до 30 символов")
    private String username;
    @Size(min = 1, max = 100, message = "Некорректная длина пароля")
    private String password;
    private boolean enabled;
    @Transient
    UserService userService;
    @Transient
    Authentication authentication;
    @Transient
    private String role;
    @Transient
    private String passwordConfirm;
    public Authority getAuthority(){
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(Roles.getRoleFromName(role));
        return authority;
    }
    public boolean passwordNotMatch(){
        return !isNullUser() && !password.equals(passwordConfirm);
    }
    public boolean userExist(){
        return !isNullUser() && userService.findById(username) != null;
    }
    public boolean noDeleteUser(){
        if(isNullUser() || authentication == null){
            return false;
        }
        if(userService.getCurrentUser(authentication).getUsername().equals(username)){
            return true;
        }
        for(String infinityUser: PassChestApplication.INFINITY_USERS){
            if(infinityUser.equals(username)) {
                return true;
            }
        }
        return false;
    }
    public boolean noEditUser(User oldUser){
        if(isNullUser() || authentication == null){
            return false;
        }
        String currentUser = userService.getCurrentUser(authentication).getUsername();
        if(currentUser.equals(username) || PassChestApplication.INFINITY_USERS[1].equals(username)){
            return !oldUser.getRole().equals(role) || oldUser.isEnabled() != enabled;
        }
        return PassChestApplication.INFINITY_USERS[0].equals(username);
    }
    private boolean isNullUser(){
        return password == null || passwordConfirm == null ||
                username == null || userService == null || role == null;
    }
    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass()) {
            return false;
        }
        return ((User) o).getUsername().equals(username);
    }
    @Override
    public String toString(){
        return String.format("User with name: %s", username);
    }

    @Override
    public int compareTo(User user) {
        return username.compareTo(user.getUsername());
    }
}
