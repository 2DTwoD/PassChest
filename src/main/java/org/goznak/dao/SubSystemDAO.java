package org.goznak.dao;

import org.goznak.models.SubSystem;
import org.goznak.models.System;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubSystemDAO extends CrudRepository<SubSystem, Integer> {
    List<SubSystem> findAllByOrderByName();
    List<SubSystem> findSubSystemByName(String name);
    List<SubSystem> findByNameContainsIgnoreCaseOrderByName(String filter);
    SubSystem findFirstById(int name);
}
