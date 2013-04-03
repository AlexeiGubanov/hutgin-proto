package com.hutgin2.dao.hibernate;

import com.hutgin2.dao.GenericEntityNameDAO;
import com.hutgin2.dao.search.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public class GenericEntityNameDAOImpl<T> extends EntityNameBaseDAO implements GenericEntityNameDAO<T> {

    public GenericEntityNameDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    public T find(String entityName, Serializable id) {
        return _get(entityName, id);
    }

    @Override
    public T[] find(String entityName, Serializable... ids) {
        return _get(entityName, ids);
    }

    @Override
    public T getReference(String entityName, Serializable id) {
        return _load(entityName, id);
    }

    @Override
    public T[] getReferences(String entityName, Serializable... ids) {
        return _load(entityName, ids);
    }

    @Override
    public boolean save(String entityName, T entity) {
        return _saveOrUpdateIsNew(entityName, entity);
    }

    @Override
    public boolean[] save(String entityName, T... entities) {
        return _saveOrUpdateIsNew(entityName, entities);
    }

    @Override
    public boolean remove(String entityName, T entity) {
        return _deleteEntity(entityName, entity);
    }

    @Override
    public void remove(String entityName, T... entities) {
        _deleteEntities(entityName, entities);
    }

    @Override
    public boolean removeById(String entityName, Serializable id) {
        return _deleteById(entityName, id);
    }

    @Override
    public void removeByIds(String entityName, Serializable... ids) {
        _deleteById(entityName, ids);
    }

    @Override
    public List<T> findAll(String entityName) {
        return _all(entityName);
    }

    @Override
    public List<T> search(ISearch search) {
        return _search(search);
    }


    @Override
    public T searchUnique(ISearch search) {
        return (T) _searchUnique(search);
    }

    @Override
    public int count(ISearch search) {
        if (search == null)
            search = new Search();
        return _count(search);
    }

    @Override
    public SearchResult searchAndCount(ISearch search) {
        return _searchAndCount(search);
    }

    @Override
    public boolean isAttached(String entityName, T entity) {
        return _sessionContains(entityName, entity);
    }

    @Override
    public void refresh(String entityName, T... entities) {
        _refresh(entityName, entities);
    }

    @Override
    public void flush() {
        _flush();
    }

    @Override
    public Filter getFilterFromExample(String entityName, T example) {
        return _getFilterFromExample(entityName, example);
    }

    @Override
    public Filter getFilterFromExample(String entityName, T example, ExampleOptions options) {
        return _getFilterFromExample(entityName, example, options);
    }


}
