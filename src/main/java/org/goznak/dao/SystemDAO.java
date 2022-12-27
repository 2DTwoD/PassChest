package org.goznak.dao;

import org.goznak.models.System;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SystemDAO extends CrudRepository<System, Integer> {
    List<System> findAllByOrderByName();
    List<System> findSystemByName(String name);
    List<System> findByNameContainsIgnoreCaseOrderByName(String filter);
    System findFirstById(int id);
}
