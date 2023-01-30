package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.dao.SystemDAO;
import org.goznak.models.CredentialsIds;
import org.goznak.models.PassSlice;
import org.goznak.models.SubSystem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class PassSliceService extends CommonService<PassSlice, Long> {
    public PassSliceService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        super(authorityDAO, passSliceDAO, subSystemDAO, systemDAO, userDAO, credentialsIdsDAO);
    }
    @Override
    public List<PassSlice> findAll() {
        List<PassSlice> passSlices = passSliceDAO.findAllByActualOrderByIdAsc(true);
        Collections.sort(passSlices);
        return passSlices;
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
        return passSliceDAO.findPassSliceBySoftName(name);
    }
    public List<PassSlice> findBySubSystemAndSoftName(SubSystem subSystem, String name) {
        return passSliceDAO.findPassSliceBySubSystemAndSoftName(subSystem, name);
    }
    public List<PassSlice> findBySoftNameAndCredentialId(String softName, CredentialsIds credentialsIds) {
        return passSliceDAO.findPassSliceBySoftNameAndCredentialsIdsOrderBySoftNameAscLastChangeDesc(softName, credentialsIds);
    }
    public PassSlice findByCredentialsIdsAndLogin(CredentialsIds credentialsIds, String login) {
        return passSliceDAO.findFirstByCredentialsIdsAndLoginAndActual(credentialsIds, login, true);
    }
    @Override
    public void save(PassSlice passSlice) {
        passSliceDAO.save(passSlice);
    }
    @Override
    public List<PassSlice> findByFilter(String filter) {
        String[] splitFilterArray = filter.split(" ");
        ArrayList<PassSlice> result = new ArrayList<>();
        List<PassSlice> passSlices = passSliceDAO.findAllByActualOrderByIdAsc(true);
        for(PassSlice passSlice: passSlices){
            for(String splitFilter: splitFilterArray) {
                splitFilter = splitFilter.toLowerCase();
                if (passSlice.getSubSystem().getName().toLowerCase().contains(splitFilter) ||
                    passSlice.getSubSystem().getSystem().getName().toLowerCase().contains(splitFilter)) {
                    if(!result.contains(passSlice)) {
                        result.add(passSlice);
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
    }
    @Override
    public void delete(PassSlice passSlice) {
        passSliceDAO.delete(passSlice);
    }
    public List<PassSlice> findByFilter(String filter, int id) {
        String[] splitFilterArray = filter.split(" ");
        ArrayList<PassSlice> result = new ArrayList<>();
        List<PassSlice> passSlices = passSliceDAO.findPassSliceBySubSystemIdAndActual(id, true);
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
        Collections.sort(result);
        return result;
    }
    public List<PassSlice> findBySubSystemId(Integer id) {
        List<PassSlice> passSlices = passSliceDAO.findPassSliceBySubSystemIdAndActual(id, true);
        Collections.sort(passSlices);
        return passSlices;
    }
    public void deleteAll(String softName, int id){
        passSliceDAO.deleteBySoftNameAndSubSystemId(softName, id);
    }
}
