package com.hutgin.entity;

public class Field extends Entity {
    private String name;
    private String description;
    private Integer type;
    private Integer size;
    private String tableName;

    public Field() {
    }

    public Field(String name) {
        this.name = name;
    }

    public Field(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return tableName + "." + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public static Table getDescriptor() {
        Table table = new Table("DD_FIELD");
        table.addField(new Field("NAME", FieldDataType.String.getCode()));
        table.addField(new Field("DESCRIPTION", FieldDataType.String.getCode()));
        table.addField(new Field("TYPE", FieldDataType.Integer.getCode()));
        table.addField(new Field("SIZE", FieldDataType.Integer.getCode()));
        table.addField(new Field("TABLE_NAME", FieldDataType.String.getCode()));
        return table;

    }
}
