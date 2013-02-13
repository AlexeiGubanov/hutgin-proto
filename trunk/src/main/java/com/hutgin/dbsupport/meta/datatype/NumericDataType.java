package com.hutgin.dbsupport.meta.datatype;

public class NumericDataType extends DataType {

    public static final String NAME = "NUMERIC";
    public static final int DEFAULT_SIZE = 5;
    public static final int DEFAULT_PRECISION = 2;

    private int precision = DEFAULT_PRECISION;

    public NumericDataType() {
        this(DEFAULT_SIZE);
    }

    public NumericDataType(int size, int precision) {
        this(size);
        this.precision = precision;
    }

    public NumericDataType(int size) {
        super(NAME, size);
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
