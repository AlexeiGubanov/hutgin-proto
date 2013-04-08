package com.hutgin2.dao.hibernate;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.GeneralEntityDAO;
import com.hutgin2.dao.search.ExampleOptions;
import com.hutgin2.dao.search.Filter;
import com.hutgin2.dao.search.ISearch;
import com.hutgin2.dao.search.SearchResult;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;
import com.hutgin2.inject.hibernate.EntitySessionFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
public class GeneralEntityDAOImpl implements GeneralEntityDAO {

    @Autowired
    private EntitySessionFactory entitySessionFactory;

    protected SessionFactory getSessionFactory() {
        return entitySessionFactory.getSessionFactory();
    }

    @Override
    public Entity find(TableMeta type, Serializable id) {
        Map<String, Object> r = new MapEntityNameDAOImpl(getSessionFactory()).find(type.getName(), id);
        return new Entity(r, type);
    }

    @Override
    public boolean save(Entity entity) {
        return new MapEntityNameDAOImpl(getSessionFactory()).save(entity.getMeta().getName(), entity);
    }

    @Override
    public boolean remove(Entity entity) {
        return new MapEntityNameDAOImpl(getSessionFactory()).remove(entity.getMeta().getName(), entity);
    }

    @Override
    public EntityCollection findAll(TableMeta type) {
        List<Map<String, Object>> r = new MapEntityNameDAOImpl(getSessionFactory()).findAll(type.getName());
        return new EntityCollection(type, r);
    }

    @Override
    public EntityCollection search(TableMeta type, ISearch search) {
        List<Map<String, Object>> r = new MapEntityNameDAOImpl(getSessionFactory()).search(search);
        return new EntityCollection(type, r);
    }

    @Override
    public Entity searchUnique(TableMeta type, ISearch search) {
        Map<String, Object> r = new MapEntityNameDAOImpl(getSessionFactory()).searchUnique(search);
        return new Entity(r, type);
    }

    @Override
    public int count(ISearch search) {
        return new MapEntityNameDAOImpl(getSessionFactory()).count(search);
    }

    @Override
    public EntityCollection searchAndCount(TableMeta type, ISearch search) {
        SearchResult r = new MapEntityNameDAOImpl(getSessionFactory()).searchAndCount(search);
        return new EntityCollection(type, r.getResult(), r.getTotalCount());
    }

    @Override
    public void refresh(Entity... entities) {
        for (Entity entity : entities)
            new MapEntityNameDAOImpl(getSessionFactory()).refresh(entity.getMeta().getName(), entity);
    }

    @Override
    public void flush() {
        new MapEntityNameDAOImpl(getSessionFactory()).flush();
    }

    @Override
    public Filter getFilterFromExample(Entity example) {
        return new MapEntityNameDAOImpl(getSessionFactory()).getFilterFromExample(example.getMeta().getName(), example);
    }

    @Override
    public Filter getFilterFromExample(Entity example, ExampleOptions options) {
        return new MapEntityNameDAOImpl(getSessionFactory()).getFilterFromExample(example.getMeta().getName(), example, options);
    }
}
