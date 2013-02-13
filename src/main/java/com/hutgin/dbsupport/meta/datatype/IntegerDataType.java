package com.hutgin.dbsupport.meta.datatype;

public class IntegerDataType extends DataType {
    public static final String NAME = "INTEGER";
    public static final int DEFAULT_SIZE = 10;

    public IntegerDataType() {
        this(DEFAULT_SIZE);
    }

    public IntegerDataType(int size) {
        super(NAME, size);
    }
}
