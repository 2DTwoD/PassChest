package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.dao.SystemDAO;
import org.goznak.models.PassSlice;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PassSliceService extends CommonService<PassSlice, Long> {
    public PassSliceService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO);
    }
    @Override
    public List<PassSlice> findAll() {
        return (List<PassSlice>) passSliceDAO.findAll();
    }

    @Override
    public PassSlice findById(Long id) {
        return passSliceDAO.findFirstById(id);
    }

    @Override
    public List<PassSlice> findByName(String name) {
        return null;
    }
    @Override
    public void save(PassSlice passSlice) {
        passSliceDAO.save(passSlice);
    }

    @Override
    public void delete(PassSlice passSlice) {
        passSliceDAO.delete(passSlice);
    }
}
