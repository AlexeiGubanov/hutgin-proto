package com.hutgin2.core.meta;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModel {
    private List<TableMeta> tables = new ArrayList<>();

    public List<TableMeta> getTables() {
        return tables;
    }

    public void setTables(List<TableMeta> tables) {
        this.tables = tables;
    }

    public TableMeta byName(String name) {
        for (TableMeta tableMeta : tables) {
            if (StringUtils.equalsIgnoreCase(name, tableMeta.getName()))
                return tableMeta;
        }
        return null;
    }

    public TableMeta getTable(String name) {
        for (TableMeta meta : tables) {
            if (StringUtils.equals(meta.getName(), name))
                return meta;
        }
        return null;
    }
}
