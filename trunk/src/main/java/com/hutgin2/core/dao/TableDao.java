package com.hutgin2.core.dao;

import com.hutgin2.core.meta.TableMeta;

import java.util.List;

public interface TableDao {

    List<TableMeta> getAll();

    void persist(TableMeta tableMeta);
}
