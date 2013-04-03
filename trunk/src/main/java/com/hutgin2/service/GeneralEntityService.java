package com.hutgin2.service;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;

import java.io.Serializable;

public interface GeneralEntityService {

    Entity find(TableMeta type, Serializable id);

    boolean save(Entity entity);

    boolean remove(Entity entity);

    EntityCollection findAll(TableMeta type);
}
