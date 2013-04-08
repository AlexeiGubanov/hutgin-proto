package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.GeneralEntityDAO;
import com.hutgin2.dao.search.ISearch;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;
import com.hutgin2.service.GeneralEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("generalEntityService")
public class GeneralEntityServiceImpl implements GeneralEntityService {

    @Autowired
    private GeneralEntityDAO entityDao;

    @Override
    public Entity find(TableMeta type, Serializable id) {
        return entityDao.find(type, id);
    }

    @Override
    public boolean save(Entity entity) {
        return entityDao.save(entity);
    }

    @Override
    public boolean remove(Entity entity) {
        return entityDao.remove(entity);
    }

    @Override
    public EntityCollection findAll(TableMeta type) {
        return entityDao.findAll(type);
    }

    @Override
    public EntityCollection search(TableMeta type, ISearch search) {
        return entityDao.search(type, search);
    }

    @Override
    public int count(ISearch search) {
        return entityDao.count(search);
    }

    @Override
    public EntityCollection searchAndCount(TableMeta type, ISearch search) {
        return entityDao.searchAndCount(type, search);
    }

    @Override
    public void refresh(Entity... entities) {
        entityDao.refresh(entities);
    }
}
