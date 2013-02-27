package com.hutgin2.meta;

@Deprecated
public enum FieldDataType {
    Integer(0), String(1);

    private int code;

    private FieldDataType(int type) {
        this.code = type;
    }

    public int getCode() {
        return code;
    }

    public static FieldDataType fromCode(int code) {
        for (FieldDataType v : FieldDataType.values()) {
            if (v.getCode() == code)
                return v;
        }
        return null;
    }
}
