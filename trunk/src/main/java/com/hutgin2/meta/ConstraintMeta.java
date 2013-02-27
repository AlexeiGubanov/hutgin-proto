package com.hutgin2.meta;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "META_CONSTRAINT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class ConstraintMeta {
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
    private ConstraintType type;
    private TableMeta table;
    private List<FieldMeta> fields;

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

    @Column(insertable = false, updatable = false, name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @Index(name = "IDX_META_CONSTRAINT_TYPE")
    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    @ManyToOne
    public TableMeta getTable() {
        return table;
    }

    public void setTable(TableMeta table) {
        this.table = table;
    }

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "META_CONSTRAINT_FIELDS_A")
    public List<FieldMeta> getFields() {
        if (fields == null)
            return new ArrayList<>();
        return fields;
    }

    public void setFields(List<FieldMeta> fields) {
        this.fields = fields;
    }
}
