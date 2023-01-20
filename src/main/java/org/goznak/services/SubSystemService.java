package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.SubSystem;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SubSystemService extends CommonService<SubSystem, Integer> {
    public SubSystemService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO, credentialsIdsDAO);
    }
    @Override
    public List<SubSystem> findAll() {
        List<SubSystem> subSystems = subSystemDAO.findAllByOrderById();
        Collections.sort(subSystems);
        return subSystems;
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
        return subSystemDAO.findSubSystemByNameIgnoreCaseOrderByNameAsc(name);
    }
    @Override
    public List<SubSystem> findByFilter(String filter) {
        String[] splitFilterArray = filter.split(" ");
        ArrayList<SubSystem> result = new ArrayList<>();
        List<SubSystem> subSystems = subSystemDAO.findAllByOrderById();
        for(SubSystem subSystem: subSystems){
            for(String splitFilter: splitFilterArray) {
                splitFilter = splitFilter.toLowerCase();
                if (subSystem.getName().toLowerCase().contains(splitFilter) ||
                        subSystem.getSystem().getName().toLowerCase().contains(splitFilter)) {
                    if(!result.contains(subSystem)) {
                        result.add(subSystem);
                    }
                }
            }
        }
        Collections.sort(result);
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
