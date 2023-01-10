package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.SubSystem;
import org.goznak.models.System;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class SubSystemService extends CommonService<SubSystem, Integer> {
    public SubSystemService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO);
    }
    @Override
    public List<SubSystem> findAll() {
        return subSystemDAO.findAllByOrderByName();
    }
    @Override
    public SubSystem findById(Integer id) {
        return subSystemDAO.findFirstById(id);
    }
    @Override
    public SubSystem findFirstByName(String name) {
        return subSystemDAO.findFirstByNameIgnoreCase(name);
    }
    @Override
    public List<SubSystem> findByName(String name) {
        return subSystemDAO.findSubSystemByNameIgnoreCaseOrderByName(name);
    }
    @Override
    public List<SubSystem> findByFilter(String filter) {
        List<SubSystem> result = new ArrayList<>();
        List<SubSystem> subSystems = subSystemDAO.findAllByOrderByName();
        for(SubSystem subSystem: subSystems){
            if(subSystem.getName().toLowerCase().contains(filter) ||
                    subSystem.getSystem().getName().toLowerCase().contains(filter)){
                result.add(subSystem);
            }
        }
        return result;
    }
    @Override
    public void save(SubSystem subSystem) {
        subSystemDAO.save(subSystem);
    }

    @Override
    public void delete(SubSystem subSystem) {
        subSystemDAO.delete(subSystem);
    }
}
