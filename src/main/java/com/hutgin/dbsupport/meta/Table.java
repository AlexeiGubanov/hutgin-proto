package com.hutgin.dbsupport.meta;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private String schema;
    private final List<Column> columns = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<Column> getColumns() {
        return columns;
    }


    public List<Constraint> getConstraints() {
        return constraints;
    }

}
