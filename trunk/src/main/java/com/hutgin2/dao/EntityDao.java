package com.hutgin2.dao;

import com.hutgin2.core.meta.TableMeta;

import java.util.List;
import java.util.Map;

public interface EntityDao {

    List getAll(TableMeta tableMeta);

    void save(TableMeta tableMeta, Map<String, Object> value);

}
