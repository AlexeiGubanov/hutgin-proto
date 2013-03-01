package com.hutgin2.meta;

import org.hibernate.annotations.Index;

import javax.persistence.*;

@Entity
@Table(name = "META_FIELD")
public class FieldMeta {

    @Id
    @Column(length = 501)
    public String getId() {
        return tableName + "." + name;
    }

    public void setId(String id) {

    }

    private String name;

    private String description;

    private String tableName;

    private Class type;

    private Integer size;

    private Integer precision;

    private TableMeta table;

    private FieldAssociationType associationType = FieldAssociationType.NONE;

    private FieldMeta fieldRef;

    private Boolean required;

    private String defaultValue;

    @Column(length = 255, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Index(name = "IDX_META_FIELD_TYPE")
    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @ManyToOne
    public TableMeta getTable() {
        return table;
    }

    public void setTable(TableMeta table) {
        this.table = table;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }


    @Column(insertable = false, updatable = false, name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Enumerated(EnumType.STRING)
    @Index(name = "IDX_META_FIELD_ASSOCIATION_TYPE")
    public FieldAssociationType getAssociationType() {
        return associationType;
    }

    public void setAssociationType(FieldAssociationType associationType) {
        this.associationType = associationType;
    }

    @ManyToOne(optional = true)
    public FieldMeta getFieldRef() {
        return fieldRef;
    }

    public void setFieldRef(FieldMeta fieldRef) {
        this.fieldRef = fieldRef;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldMeta fieldMeta = (FieldMeta) o;

        if (!name.equals(fieldMeta.name)) return false;
        if (!tableName.equals(fieldMeta.tableName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + tableName.hashCode();
        return result;
    }


    private boolean isProcessed;

    @Transient
    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
