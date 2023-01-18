package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.models.CredentialsIds;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CredentialsIdsService  extends CommonService<CredentialsIds, Integer>{
    public CredentialsIdsService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO, credentialsIdsDAO);
    }

    @Override
    public List<CredentialsIds> findAll() {
        return credentialsIdsDAO.findAllByOrderById();
    }

    @Override
    public CredentialsIds findById(Integer id) {
        return credentialsIdsDAO.findFirstById(id);
    }

    @Override
    public CredentialsIds findFirstByName(String name) {
        return null;
    }

    @Override
    public List<CredentialsIds> findByName(String name) {
        return null;
    }

    @Override
    public List<CredentialsIds> findByFilter(String name) {
        return null;
    }

    @Override
    public void save(CredentialsIds credentialsIds) {
        credentialsIdsDAO.save(credentialsIds);
    }

    @Override
    public void delete(CredentialsIds credentialsIds) {
        credentialsIdsDAO.delete(credentialsIds);
    }
}
