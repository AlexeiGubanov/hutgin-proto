package com.hutgin.dbsupport.meta.datatype;

public class IntegerDataType extends DataType {
    public static final String NAME = "INTEGER";
    public static final int DEFAULT_SIZE = 0;

    public IntegerDataType() {
        this(DEFAULT_SIZE);
    }

    public IntegerDataType(int size) {
        super(NAME, size);
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder(getName());
        if (getSize() != DEFAULT_SIZE) {
            sb.append("(").append(getSize()).append(")");
        }
        return sb.toString();
    }

    @Override
    public String getValueAsString(Object value) {
        return value.toString();
    }
}
