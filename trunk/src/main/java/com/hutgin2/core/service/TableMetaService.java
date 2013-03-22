package com.hutgin2.core.service;

import com.hutgin2.core.meta.TableMeta;

import java.util.List;

public interface TableMetaService {

    List<TableMeta> getAll();

    void save(TableMeta tableMeta);


}
