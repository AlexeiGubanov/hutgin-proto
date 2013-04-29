package com.hutgin2.core.service.impl;

import com.hutgin2.core.dao.GenericDAO;
import com.hutgin2.core.dao.search.ISearch;
import com.hutgin2.core.dao.search.SearchResult;
import com.hutgin2.core.service.GenericService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

    protected GenericDAO<T, ID> genericDAO;

    protected GenericServiceImpl(GenericDAO<T, ID> dao) {
        this.genericDAO = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public T find(ID id) {
        return genericDAO.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T[] find(ID... ids) {
        return genericDAO.find(ids);
    }

    @Override
    public boolean save(T entity) {
        return genericDAO.save(entity);
    }

    @Override
    public boolean[] save(T... entities) {
        return genericDAO.save(entities);
    }

    @Override
    public boolean remove(T entity) {
        return genericDAO.remove(entity);
    }

    @Override
    public void remove(T... entities) {
        genericDAO.remove(entities);
    }

    @Override
    public boolean removeById(ID id) {
        return genericDAO.removeById(id);
    }

    @Override
    public void removeByIds(ID... ids) {
        genericDAO.removeByIds(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return genericDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public <RT> List<RT> search(ISearch search) {
        return genericDAO.search(search);
    }

    @Override
    @Transactional(readOnly = true)
    public int count(ISearch search) {
        return genericDAO.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public <RT> SearchResult<RT> searchAndCount(ISearch search) {
        return genericDAO.searchAndCount(search);
    }

    @Override
    @Transactional(readOnly = true)
    public void refresh(T... entities) {
        genericDAO.refresh(entities);
    }
}
