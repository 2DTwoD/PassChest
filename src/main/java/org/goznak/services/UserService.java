package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.Authority;
import org.goznak.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserService extends CommonService<User, String>{

    public UserService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAllByOrderByUsername();
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
        return userDAO.findByUsernameContainsIgnoreCaseOrderByUsername(filter);
    }
    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
    public User getCurrentUser(Authentication authentication){
        if (authentication != null) {
            return findById(authentication.getName());
        } else {
            return findById("Guest");
        }
    }
}
