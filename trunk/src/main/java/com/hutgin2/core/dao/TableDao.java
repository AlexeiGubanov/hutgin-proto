package com.hutgin2.core.dao;

import com.hutgin2.core.meta.TableMeta;

import java.util.List;

public interface TableDao { //extends GenericDAO<TableMeta,String> {

    List<TableMeta> findAll();

    void save(TableMeta tableMeta);
}
