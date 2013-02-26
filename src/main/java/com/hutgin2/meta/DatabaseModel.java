package com.hutgin2.meta;

import java.util.List;

public class DatabaseModel {
    private List<TableMeta> tables;

    public List<TableMeta> getTables() {
        return tables;
    }

    public void setTables(List<TableMeta> tables) {
        this.tables = tables;
    }
}
