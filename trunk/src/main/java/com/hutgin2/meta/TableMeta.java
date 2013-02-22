package com.hutgin2.meta;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DD_TABLE")
public class TableMeta {

    @Id
    @Column(length = 255)
    private String name;

    @OneToMany(targetEntity = FieldMeta.class, mappedBy = "table")
    private List<FieldMeta> fields;

    public TableMeta() {
    }

    public TableMeta(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldMeta> getFields() {
        return fields;
    }

    public void setFields(List<FieldMeta> fields) {
        this.fields = fields;
    }

}
