package com.hutgin2.core.meta;

import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@DiscriminatorValue("FK")
// unused yet
public class ContstraintFKMeta extends ConstraintMeta {

    public static enum FK_ACTION {NO_ACTION, CASCADE, SET_NULL}

    private FK_ACTION updateAction = FK_ACTION.NO_ACTION;

    private FK_ACTION deleteAction = FK_ACTION.NO_ACTION;

    private List<FieldMeta> fieldsB;

    private TableMeta tableB;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "META_CONSTRAINT_FIELDS_B")
    public List<FieldMeta> getFieldsB() {
        if (fieldsB == null)
            return new ArrayList<>();
        return fieldsB;
    }

    public void setFieldsB(List<FieldMeta> fieldsB) {
        this.fieldsB = fieldsB;
    }

    @ManyToOne
    public TableMeta getTableB() {
        return tableB;
    }

    public void setTableB(TableMeta tableB) {
        this.tableB = tableB;
    }
}
