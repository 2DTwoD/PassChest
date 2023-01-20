package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.System;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class SystemService extends CommonService<System, Integer> {

    public SystemService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO, credentialsIdsDAO);
    }
    @Override
    public List<System> findAll() {
        List<System> systems = systemDAO.findAllByOrderByName();
        Collections.sort(systems);
        return systems;
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
        String[] splitFilterArray = filter.split(" ");
        ArrayList<System> result = new ArrayList<>();
        List<System> systems = systemDAO.findAllByOrderByName();
        for(System system: systems){
            for(String splitFilter: splitFilterArray) {
                splitFilter = splitFilter.toLowerCase();
                if (system.getName().toLowerCase().contains(splitFilter)) {
                    if(!result.contains(system)) {
                        result.add(system);
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
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
