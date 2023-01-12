package org.goznak.dao;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import org.goznak.models.PassSlice;
import org.goznak.models.SubSystem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassSliceDAO extends CrudRepository<PassSlice, Long> {

    List<PassSlice> findAllByOrderById();
    List<PassSlice> findPassSliceBySubSystemIdAndActualOrderBySoftName(Integer id, boolean actual);
    List<PassSlice> findPassSliceBySoftNameOrderBySoftName(String softName);
    List<PassSlice> findBySoftNameContainsIgnoreCaseOrderBySoftName(String filter);
    @Transactional
    List<PassSlice> findBySoftNameContainsIgnoreCaseAndSubSystemIdAndActualOrderBySoftName(String softName, int id, boolean actual);
    PassSlice findFirstById(long id);
    PassSlice findFirstBySubSystem(SubSystem subSystem);
    @Transactional
    void deleteBySoftNameAndSubSystemId(String softName, int subSystemId);
}
