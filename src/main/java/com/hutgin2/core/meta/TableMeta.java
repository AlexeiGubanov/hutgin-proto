package com.hutgin2.core.meta;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "META_TABLE")
public class TableMeta extends MetaEntity {

    public static final String PROPERTY_LAZY = "lazy";


    private String name;

    private String tableName;

    private Set<FieldMeta> fields;

    private Set<ConstraintMeta> constraints;

    /**
     * Determines that corresponding column in DB is not presented.
     */
    private boolean virtual = false;

    public TableMeta() {
    }

    public TableMeta(String name) {
        this.name = name;
    }

    @Id
    @Column(length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 255)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @OneToMany(targetEntity = FieldMeta.class, mappedBy = "table", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public Set<FieldMeta> getFields() {
        if (this.fields == null)
            this.fields = new HashSet<>();
        return fields;
    }

    public void setFields(Set<FieldMeta> fields) {
        this.fields = fields;
    }

    @OneToMany(targetEntity = ConstraintMeta.class, mappedBy = "table", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public Set<ConstraintMeta> getConstraints() {
        if (this.constraints == null)
            this.constraints = new HashSet<>();
        return constraints;
    }

    @Transient
    public Set<ConstraintIDXMeta> getConstraintIDXs() {
        Set<ConstraintIDXMeta> result = new HashSet<>();
        for (ConstraintMeta constraintMeta : getConstraints()) {
            if (constraintMeta instanceof ConstraintIDXMeta) {
                result.add((ConstraintIDXMeta) constraintMeta);
            }
        }
        return result;
    }

    @Transient
    public Set<ConstraintUQMeta> getConstraintUQs() {
        Set<ConstraintUQMeta> result = new HashSet<>();
        for (ConstraintMeta constraintMeta : getConstraints()) {
            if (constraintMeta instanceof ConstraintUQMeta) {
                result.add((ConstraintUQMeta) constraintMeta);
            }
        }
        return result;
    }

    public void setConstraints(Set<ConstraintMeta> indices) {
        this.constraints = indices;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableMeta tableMeta = (TableMeta) o;

        if (!name.equals(tableMeta.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "TableMeta{" +
                "name='" + name + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
