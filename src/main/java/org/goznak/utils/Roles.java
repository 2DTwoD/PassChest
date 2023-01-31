package org.goznak.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Roles {
    ROLE_ADMIN("Администратор"),
    ROLE_GUEST("Гость"),
    ROLE_USER("Пользователь");
    public static final String[] rolesForCredentials = {
            "Администратор",
            "Гость",
            "Инженер",
            "Оператор",
            "Пользователь",
            "Программист",
            "Сервис",
            "Супер пользователь",
            "Технолог"
    };
    private final String name;

    Roles(String name) {
        this.name = name;
    }
    public static Roles getRoleFromName(String name){
        for(Roles roles: Roles.values()){
            if(name.equals(roles.toString())){
                return roles;
            }
        }
        return ROLE_GUEST;
    }
    public static List<String> getRolesNameList(){
        return Arrays.stream(Roles.values()).map(p -> p.name).collect(Collectors.toList());
    }
    @Override
    public String toString(){
        return name;
    }
}
