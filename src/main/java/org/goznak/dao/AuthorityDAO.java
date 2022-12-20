package org.goznak.dao;

import org.goznak.models.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityDAO  extends CrudRepository<Authority, String> {
}
