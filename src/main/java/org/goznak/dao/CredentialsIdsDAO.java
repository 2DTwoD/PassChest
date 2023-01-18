package org.goznak.dao;

import org.goznak.models.CredentialsIds;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CredentialsIdsDAO extends CrudRepository<CredentialsIds, Integer> {
    List<CredentialsIds> findAllByOrderById();
    CredentialsIds findFirstById(int id);
}
