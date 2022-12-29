package org.goznak.dao;

import org.goznak.models.PassSlice;
import org.goznak.models.SubSystem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassSliceDAO extends CrudRepository<PassSlice, Long> {

    List<PassSlice> findAllByOrderById();
    List<PassSlice> findPassSliceBySubSystemId(Integer id);
    PassSlice findFirstById(long id);
    PassSlice findFirstBySubSystem(SubSystem subSystem);
}
