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
        return passSliceDAO.findAllByOrderById();
    }

    @Override
    public PassSlice findById(Long id) {
        return passSliceDAO.findFirstById(id);
    }
    @Override
    public PassSlice findFirstByName(String name) {
        return null;
    }
    @Override
    public List<PassSlice> findByName(String name) {
        return passSliceDAO.findPassSliceBySoftNameOrderBySoftName(name);
    }
    @Override
    public void save(PassSlice passSlice) {
        passSliceDAO.save(passSlice);
    }
    @Override
    public List<PassSlice> findByFilter(String filter) {
        return passSliceDAO.findBySoftNameContainsIgnoreCaseOrderBySoftName(filter);
    }
    @Override
    public void delete(PassSlice passSlice) {
        passSliceDAO.delete(passSlice);
    }
    public List<PassSlice> findBySubSystemId(Integer id) {
        return passSliceDAO.findPassSliceBySubSystemId(id);
    }
}
