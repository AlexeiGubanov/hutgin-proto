package com.hutgin.dbsupport.meta.datatype;

public abstract class DataType {

    private String name;
    private int size;

    protected DataType(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public abstract String toSql();

    public abstract String getValueAsString(Object value);
}
