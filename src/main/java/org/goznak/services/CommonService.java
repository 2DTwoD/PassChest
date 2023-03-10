package org.goznak.services;

import org.goznak.dao.*;
import org.goznak.dao.SystemDAO;

import java.util.List;
public abstract class CommonService<T, ID> {
    final
    AuthorityDAO authorityDAO;
    final
    PassSliceDAO passSliceDAO;
    final
    SubSystemDAO subSystemDAO;
    final
    SystemDAO systemDAO;
    final
    UserDAO userDAO;
    final
    CredentialsIdsDAO credentialsIdsDAO;
    public CommonService(AuthorityDAO authorityDAO, PassSliceDAO passSliceDAO, SubSystemDAO subSystemDAO, SystemDAO systemDAO, UserDAO userDAO, CredentialsIdsDAO credentialsIdsDAO) {
        this.authorityDAO = authorityDAO;
        this.passSliceDAO = passSliceDAO;
        this.subSystemDAO = subSystemDAO;
        this.systemDAO = systemDAO;
        this.userDAO = userDAO;
        this.credentialsIdsDAO = credentialsIdsDAO;
    }

    abstract public List<T> findAll();
    abstract public T findById(ID id);
    abstract public T findFirstByName(String name);
    abstract public List<T> findByName(String name);
    abstract public List<T> findByFilter(String name);
    abstract public void save(T t);
    abstract public void delete(T t);
}
