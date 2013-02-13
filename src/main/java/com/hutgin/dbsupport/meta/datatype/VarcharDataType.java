package com.hutgin.dbsupport.meta.datatype;

public class VarcharDataType extends DataType {

    public static final String NAME = "VARCHAR";
    public static final int DEFAULT_SIZE = 255;

    public VarcharDataType() {
        this(DEFAULT_SIZE);
    }

    public VarcharDataType(int size) {
        super(NAME, size);
    }

}
