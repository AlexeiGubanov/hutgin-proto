package com.hutgin2.dao.hibernate;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.GeneralEntityDAO;
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
}
