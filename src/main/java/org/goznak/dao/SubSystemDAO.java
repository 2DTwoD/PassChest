package org.goznak.dao;

import org.goznak.models.SubSystem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubSystemDAO extends CrudRepository<SubSystem, Integer> {
    List<SubSystem> findAllByOrderById();
    List<SubSystem> findSubSystemByNameIgnoreCaseOrderByNameAsc(String name);
    List<SubSystem> findByNameContainsIgnoreCaseOrderByName(String filter);
    SubSystem findFirstById(int id);
    SubSystem findFirstByNameIgnoreCase(String name);
}
