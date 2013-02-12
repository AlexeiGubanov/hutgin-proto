package com.hutgin.entity;

import java.util.ArrayList;
import java.util.List;

public class Table extends Entity {

    private String name;

    private final List<Field> fields = new ArrayList<Field>();

    public Table() {
    }

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
        field.setTableName(this.name);
    }
}
