package com.hutgin2.service;

import com.hutgin2.core.meta.TableMeta;

import java.util.List;
import java.util.Map;

public interface EntityService {

    List getAll(TableMeta tableMeta);

    void save(TableMeta tableMeta, Map<String, Object> value);
}
