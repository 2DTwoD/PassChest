package org.goznak.dao;

import jakarta.transaction.Transactional;
import org.goznak.models.CredentialsIds;
import org.goznak.models.PassSlice;
import org.goznak.models.SubSystem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassSliceDAO extends CrudRepository<PassSlice, Long> {

    List<PassSlice> findAllByActualOrderByIdAsc(boolean actual);
    List<PassSlice> findPassSliceBySubSystemIdAndActual(Integer id, boolean actual);
    List<PassSlice> findPassSliceBySoftName(String softName);
    List<PassSlice> findPassSliceBySoftNameAndCredentialsIdsOrderBySoftNameAscLastChangeDesc(String softName, CredentialsIds credentialsIds);
    List<PassSlice> findPassSliceBySubSystemAndSoftName(SubSystem subSystem ,String softName);
    PassSlice findFirstByCredentialsIdsAndLoginAndActual(CredentialsIds credentialsIds, String login, boolean actual);
    PassSlice findFirstById(long id);
    @Transactional
    void deleteBySoftNameAndSubSystemId(String softName, int subSystemId);
}
