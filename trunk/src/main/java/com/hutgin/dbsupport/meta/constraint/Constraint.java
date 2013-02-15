package com.hutgin.dbsupport.meta.constraint;

import com.hutgin.dbsupport.meta.Column;
import com.hutgin.dbsupport.meta.DbMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Constraint implements DbMeta {
    private String name;

    private final List<Column> columns = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }


    public boolean isOneColumn() {
        return getColumns().size() == 1;
    }

    protected Constraint(String name, Column... columns) {
        this.name = name;
        this.columns.addAll(Arrays.asList(columns));
    }
//
//    public abstract String toSql();
}
