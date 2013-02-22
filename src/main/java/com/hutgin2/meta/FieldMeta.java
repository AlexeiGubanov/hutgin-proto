package com.hutgin2.meta;

import org.hibernate.annotations.Index;

import javax.persistence.*;

@Entity
@Table(name = "DD_FIELD")
public class FieldMeta {
    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 1024)
    private String description;

    @Enumerated
    @Index(name = "typeIdx")
    private FieldDataType type;

    private Integer size;

    @ManyToOne
    private TableMeta table;

    public FieldMeta() {
    }

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //    @Id
//    @Column(length = 500)
//    public String getId() {
//        return getTable().getName() + getName();
//    }

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

    public FieldDataType getType() {
        return type;
    }

    public void setType(FieldDataType type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public TableMeta getTable() {
        return table;
    }

    public void setTable(TableMeta table) {
        this.table = table;
    }

}
