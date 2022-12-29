package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.System;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SystemService extends CommonService<System, Integer> {

    public SystemService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO);
    }
    @Override
    public List<System> findAll() {
        return systemDAO.findAllByOrderByName();
    }
    @Override
    public System findById(Integer id) {
        return systemDAO.findFirstById(id);
    }

    @Override
    public System findFirstByName(String name) {
        return systemDAO.findFirstByNameIgnoreCase(name);
    }

    @Override
    public List<System> findByName(String name) {
        return systemDAO.findSystemByNameIgnoreCaseOrderByName(name);
    }

    public List<System> findByFilter(String filter) {
        return systemDAO.findByNameContainsIgnoreCaseOrderByName(filter);
    }

    @Override
    public void save(System system) {
        systemDAO.save(system);
    }

    @Override
    public void delete(System system) {
        systemDAO.delete(system);
    }
}
