package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.Authority;
import org.goznak.models.SubSystem;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<SubSystem> findByName(String name) {
        return subSystemDAO.findSubSystemByName(name);
    }
    @Override
    public List<SubSystem> findByFilter(String filter) {
        return subSystemDAO.findByNameContainsIgnoreCaseOrderByName(filter);
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
