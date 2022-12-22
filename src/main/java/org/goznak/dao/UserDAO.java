package org.goznak.dao;

import org.goznak.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface UserDAO extends CrudRepository<User, String> {
    List<User> findAllByOrderByUsername();
    List<User> findUserByUsername(String name);
    User findFirstByUsername(String name);
}
