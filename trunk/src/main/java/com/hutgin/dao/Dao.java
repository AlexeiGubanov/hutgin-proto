package com.hutgin.dao;

import com.hutgin.entity.Entity;
import com.hutgin.entity.Table;

import java.util.List;
import java.util.Map;

public interface Dao {

    public <T extends Map<String, Object>> List<T> findAll(Table type);

    Entity get(Table type, Object id);
}
