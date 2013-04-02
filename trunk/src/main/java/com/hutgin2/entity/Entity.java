package com.hutgin2.entity;

import com.hutgin2.core.meta.TableMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Entity extends HashMap<String, Object> {


    private TableMeta meta;

    public Entity(Map<String, Object> inner, TableMeta meta) {
        super(inner);
        this.meta = meta;
    }

    public Entity(TableMeta meta) {
        this(Collections.EMPTY_MAP, meta);
    }

    public TableMeta getMeta() {
        return meta;
    }

    public void setMeta(TableMeta meta) {
        this.meta = meta;
    }
}
