package com.hutgin.dbsupport.meta;

import com.hutgin.dbsupport.meta.datatype.DataType;

public class Column implements DbMeta {
    private String name;
    private DataType type;
    private Object defaultValue;
    private boolean required;

    public Column(String name, DataType dataType) {
        this.name = name;
        this.type = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
