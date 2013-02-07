package com.hutgin.dao;

import com.hutgin.entity.Entity;
import com.hutgin.entity.Table;

import java.util.List;

public interface Dao {

    List<Entity> findAll(Table type);

    Entity get(Table type, Object id);
}
