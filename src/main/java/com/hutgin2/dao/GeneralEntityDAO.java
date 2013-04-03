package com.hutgin2.dao;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;

import java.io.Serializable;

public interface GeneralEntityDAO {

    Entity find(TableMeta type, Serializable id);

    boolean save(Entity entity);

    boolean remove(Entity entity);

    EntityCollection findAll(TableMeta type);


}
