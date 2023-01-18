package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.dao.SystemDAO;
import org.goznak.models.CredentialsIds;
import org.goznak.models.PassSlice;
import org.goznak.models.SubSystem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PassSliceService extends CommonService<PassSlice, Long> {
    public PassSliceService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO, credentialsIdsDAO);
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
    public List<PassSlice> findBySubSystemAndSoftName(SubSystem subSystem, String name) {
        return passSliceDAO.findPassSliceBySubSystemAndSoftNameOrderBySoftNameAscLastChangeDesc(subSystem, name);
    }
    public List<PassSlice> findBySoftNameAndRole(String softName, CredentialsIds credentialsIds) {
        return passSliceDAO.findPassSliceBySoftNameAndCredentialsIdsOrderBySoftNameAscLastChangeDesc(softName, credentialsIds);
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
        List<PassSlice> passSlices = passSliceDAO.findPassSliceBySubSystemIdAndActualOrderBySoftNameAscLoginAsc(id, true);
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
        return passSliceDAO.findPassSliceBySubSystemIdAndActualOrderBySoftNameAscLoginAsc(id, true);
    }
    public void deleteAll(String softName, int id){
        passSliceDAO.deleteBySoftNameAndSubSystemId(softName, id);
    }
}
