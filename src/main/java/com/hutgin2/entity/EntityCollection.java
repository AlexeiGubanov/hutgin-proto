package com.hutgin2.entity;

import com.hutgin2.core.meta.TableMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EntityCollection extends ArrayList<Map<String, Object>> {
    private TableMeta meta;

    public EntityCollection(TableMeta meta) {
        this(meta, Collections.EMPTY_LIST);
    }

    public EntityCollection(TableMeta meta, List<Map<String, Object>> res) {
        super(res);
        this.meta = meta;
    }

    public TableMeta getMeta() {
        return meta;
    }

    public void setMeta(TableMeta meta) {
        this.meta = meta;
    }
}
