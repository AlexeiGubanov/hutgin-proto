package com.hutgin2.dao.hibernate;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.GeneralEntityDAO;
import com.hutgin2.entity.Entity;
import com.hutgin2.inject.hibernate.EntitySessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class GeneralEntityDAOImpl implements GeneralEntityDAO {

    @Autowired
    private EntitySessionFactory entitySessionFactory;

    @Override
    public Entity find(TableMeta type, Serializable id) {
        Map<String, Object> r = new GeneralMapDAOImpl(type, entitySessionFactory.getSessionFactory()).find(id);
        return new Entity(r, type);
    }
}
