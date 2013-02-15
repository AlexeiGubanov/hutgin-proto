package com.hutgin.dbsupport.meta.constraint;

import com.hutgin.dbsupport.meta.Column;
import com.hutgin.dbsupport.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// TODO check if can reference to multiply columns
public class ForeignKeyConstraint extends Constraint {

    public static enum FK_ACTION {NO_ACTION, CASCADE, SET_NULL}

    public ForeignKeyConstraint(String name, Column column, Table toTable, Column toColumn) {
        this(name, new Column[]{column}, toTable, new Column[]{toColumn});
    }

    public ForeignKeyConstraint(String name, Column[] columns, Table toTable, Column[] toColumns) {
        super(name, columns);
        this.toTable = toTable;
        this.references.addAll(Arrays.asList(toColumns));
    }

    private FK_ACTION updateAction = FK_ACTION.NO_ACTION;
    private FK_ACTION deleteAction = FK_ACTION.NO_ACTION;
    private final List<Column> references = new ArrayList<>();
    private Table toTable;


    public FK_ACTION getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(FK_ACTION updateAction) {
        this.updateAction = updateAction;
    }

    public FK_ACTION getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(FK_ACTION deleteAction) {
        this.deleteAction = deleteAction;
    }

    public List<Column> getReferences() {
        return references;
    }

    public Table getToTable() {
        return toTable;
    }

    public void setToTable(Table toTable) {
        this.toTable = toTable;
    }

}
