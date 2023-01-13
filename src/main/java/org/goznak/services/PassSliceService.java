package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.dao.SystemDAO;
import org.goznak.models.PassSlice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        return passSliceDAO.findPassSliceBySoftNameOrderBySoftNameAscLastChangeDesc(name);
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
    public List<PassSlice> findByFilter(String filter, int id) {
        String[] splitFilterArray = filter.split(" ");
        ArrayList<PassSlice> result = new ArrayList<>();
        List<PassSlice> passSlices = passSliceDAO.findPassSliceBySubSystemIdAndActualOrderBySoftName(id, true);
        for(PassSlice passSlice: passSlices){
            for(String splitFilter: splitFilterArray) {
                splitFilter = splitFilter.toLowerCase();
                if (passSlice.getSoftName().toLowerCase().contains(splitFilter)) {
                    if(!result.contains(passSlice)) {
                        result.add(passSlice);
                    }
                }
            }
        }
        return result;
    }
    public List<PassSlice> findBySubSystemId(Integer id) {
        return passSliceDAO.findPassSliceBySubSystemIdAndActualOrderBySoftName(id, true);
    }
    public void deleteAll(String softName, int id){
        passSliceDAO.deleteBySoftNameAndSubSystemId(softName, id);
    }
}
