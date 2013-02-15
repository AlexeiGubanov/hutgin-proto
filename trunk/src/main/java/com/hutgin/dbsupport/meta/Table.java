package com.hutgin.dbsupport.meta;

import com.hutgin.dbsupport.meta.constraint.Constraint;

import java.util.ArrayList;
import java.util.List;

public class Table implements DbMeta {
    private String name;
    private String schema;
    private final List<Column> columns = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }

    public Table(String schema, String name) {
        this.name = name;
        this.schema = schema;
    }

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

    public void addColumn(Column column) {
        this.getColumns().add(column);
    }

    public void addConstraint(Constraint constraint) {
        this.getConstraints().add(constraint);
    }
}
