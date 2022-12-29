package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.Authority;
import org.goznak.utils.Roles;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AuthorityService extends CommonService<Authority, String> {
    public AuthorityService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO);
    }

    @Override
    public List<Authority> findAll() {
        return authorityDAO.findAllByOrderByAuthority();
    }

    @Override
    public Authority findById(String name) {
        return authorityDAO.findFirstByUsername(name);
    }

    @Override
    public Authority findFirstByName(String name) {
        return authorityDAO.findFirstByAuthority(Roles.getRoleFromName(name));
    }

    @Override
    public List<Authority> findByName(String name) {
        return authorityDAO.findAuthoritiesByAuthority(Roles.getRoleFromName(name));
    }

    @Override
    public List<Authority> findByFilter(String filter) {
        return null;
    }

    @Override
    public void save(Authority authority) {
        authorityDAO.save(authority);
    }

    @Override
    public void delete(Authority authority) {
        authorityDAO.delete(authority);
    }
}
