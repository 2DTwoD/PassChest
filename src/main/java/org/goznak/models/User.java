package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.goznak.services.UserService;
import org.goznak.utils.Roles;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "users")
@Component
public class User {
    @Id
    @Size(min = 3, max = 30, message = "Имя пользователя должно содержать от 3 до 30 символов")
    private String username;
    @Size(min = 1, max = 100, message = "Некорректная длина пароля")
    private String password;
    private boolean enabled;
    @Transient
    UserService userService;
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
    private boolean isNullUser(){
        return password == null || passwordConfirm == null ||
                username == null || userService == null || role == null;
    }
}
