package org.goznak.dao;

import org.goznak.models.Authority;
import org.goznak.utils.Roles;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorityDAO extends CrudRepository<Authority, String> {
    List<Authority> findAllByOrderByAuthority();
    List<Authority> findAuthoritiesByAuthority(Roles role);
    Authority findFirstByAuthority(Roles role);
}
