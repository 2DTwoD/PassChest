package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class UserService extends CommonService<User, String>{

    public UserService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO, credentialsIdsDAO);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userDAO.findAllByOrderByUsername();
        Collections.sort(users);
        return users;
    }

    @Override
    public User findById(String username) {
        return userDAO.findFirstByUsername(username);
    }

    @Override
    public User findFirstByName(String name) {
        return null;
    }

    @Override
    public List<User> findByName(String name) {
        return userDAO.findUserByUsername(name);
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public List<User> findByFilter(String filter) {
        String[] splitFilterArray = filter.split(" ");
        ArrayList<User> result = new ArrayList<>();
        List<User> users = userDAO.findAllByOrderByUsername();
        for(User user: users){
            for(String splitFilter: splitFilterArray) {
                splitFilter = splitFilter.toLowerCase();
                if (user.getUsername().toLowerCase().contains(splitFilter)) {
                    if(!result.contains(user)) {
                        result.add(user);
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
    }
    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
    public User getCurrentUser(Authentication authentication){
        if (authentication != null) {
            return findById(authentication.getName());
        } else {
            return findById("Гость");
        }
    }
}
