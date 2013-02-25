package com.hutgin2.dao;

import com.hutgin2.meta.TableMeta;

import java.util.List;

public interface TableDao {

    List<TableMeta> getAll();

    void persist(TableMeta tableMeta);
}
