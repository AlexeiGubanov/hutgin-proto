package com.hutgin2.entity;

import com.hutgin2.core.meta.TableMeta;

import java.util.ArrayList;

public class EntityCollection extends ArrayList<Entity> {
    private TableMeta meta;

    public EntityCollection(TableMeta meta) {
        this.meta = meta;
    }

    public TableMeta getMeta() {
        return meta;
    }

    public void setMeta(TableMeta meta) {
        this.meta = meta;
    }
}
