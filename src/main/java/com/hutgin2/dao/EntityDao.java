package com.hutgin2.dao;

import com.hutgin2.core.meta.TableMeta;

import java.util.List;

public interface EntityDao {

    List getAll(TableMeta tableMeta);

}
