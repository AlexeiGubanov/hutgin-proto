package com.hutgin2.core.dao.hibernate;


import com.hutgin2.core.dao.DAOUtil;
import com.hutgin2.core.dao.GenericDAO;
import com.hutgin2.core.dao.search.*;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of <code>GenericDAO</code> using Hibernate.
 * The SessionFactory property is annotated for automatic resource injection.
 *
 * @param <T>  The type of the domain object for which this instance is to be
 *             used.
 * @param <ID> The type of the id of the domain object for which this instance is
 *             to be used.
 * @author dwolverton
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<T, ID extends Serializable> extends
        HibernateBaseDAO implements GenericDAO<T, ID> {

    protected Class<T> persistentClass = (Class<T>) DAOUtil.getTypeArguments(GenericDAOImpl.class, this.getClass()).get(0);

    public int count(ISearch search) {
        if (search == null)
            search = new Search();
        return _count(persistentClass, search);
    }

    public T find(Serializable id) {
        return _get(persistentClass, id);
    }

    public T[] find(Serializable... ids) {
        return _get(persistentClass, ids);
    }

    public List<T> findAll() {
        return _all(persistentClass);
    }

    public void flush() {
        _flush();
    }

    public T getReference(Serializable id) {
        return _load(persistentClass, id);
    }

    public T[] getReferences(Serializable... ids) {
        return _load(persistentClass, ids);
    }

    public boolean isAttached(T entity) {
        return _sessionContains(entity);
    }

    public void refresh(T... entities) {
        _refresh(entities);
    }

    public boolean remove(T entity) {
        return _deleteEntity(entity);
    }

    public void remove(T... entities) {
        _deleteEntities(entities);
    }

    public boolean removeById(Serializable id) {
        return _deleteById(persistentClass, id);
    }

    public void removeByIds(Serializable... ids) {
        _deleteById(persistentClass, ids);
    }

    public boolean save(T entity) {
        return _saveOrUpdateIsNew(entity);
    }

    public boolean[] save(T... entities) {
        return _saveOrUpdateIsNew(entities);
    }

    public <RT> List<RT> search(ISearch search) {
        if (search == null)
            return (List<RT>) findAll();
        return _search(persistentClass, search);
    }

    public <RT> SearchResult<RT> searchAndCount(ISearch search) {
        if (search == null) {
            SearchResult<RT> result = new SearchResult<RT>();
            result.setResult((List<RT>) findAll());
            result.setTotalCount(result.getResult().size());
            return result;
        }
        return _searchAndCount(persistentClass, search);
    }

    public <RT> RT searchUnique(ISearch search) {
        return (RT) _searchUnique(persistentClass, search);
    }

    public Filter getFilterFromExample(T example) {
        return _getFilterFromExample(example);
    }

    public Filter getFilterFromExample(T example, ExampleOptions options) {
        return _getFilterFromExample(example, options);
    }
}
