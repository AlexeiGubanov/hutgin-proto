package com.hutgin2.inject.hibernate.binder;

import com.hutgin2.core.meta.FieldMeta;
import org.hibernate.metamodel.relational.Datatype;
import org.hibernate.metamodel.relational.Size;
import org.hibernate.metamodel.source.binder.ColumnSource;

public class ColumnSourceImpl implements ColumnSource {

    private FieldMeta fieldMeta;

    public ColumnSourceImpl(FieldMeta fieldMeta) {
        this.fieldMeta = fieldMeta;
    }

    @Override
    public String getName() {
        return fieldMeta.getName();
    }

    @Override
    public String getReadFragment() {
        // null currently, TODO check possibility to supply it in FieldMeta
        return null;
    }

    @Override
    public String getWriteFragment() {
        // null currently, TODO same as getReadFragment
        return null;
    }

    @Override
    public boolean isNullable() {
        return !fieldMeta.isRequired();
    }

    @Override
    public String getDefaultValue() {
        return fieldMeta.getInsertDefaultValue();
    }

    @Override
    public String getSqlType() {
        return null;  // TODO null currently, wait for ColumnValuesSourceImpl implementation to see, also check possibility to supply it in FieldMeta
    }

    @Override
    public Datatype getDatatype() {
        return null;  //TODO same as getSqlType
    }

    @Override
    public Size getSize() {
        // TODO fill regarding fieldMeta type is number or not (for numbers length means scale)
        return new Size(
                fieldMeta.getPrecision() != null ? fieldMeta.getPrecision().intValue() : -1,
                fieldMeta.getSize() != null ? fieldMeta.getSize().intValue() : -1,
                fieldMeta.getSize() != null ? fieldMeta.getSize().intValue() : -1,
                Size.LobMultiplier.NONE
        );
    }

    @Override
    public boolean isUnique() {
        return fieldMeta.isUniq();
    }

    @Override
    public String getCheckCondition() {
        // null currently, TODO same as getReadFragment
        return null;
    }

    @Override
    public String getComment() {
        return fieldMeta.getDescription();
    }

    @Override
    public boolean isIncludedInInsert() {
        return true;// TODO check against virtual,check possibilty to support this flag
    }

    @Override
    public boolean isIncludedInUpdate() {
        return true; // TODO check against virtual,check possibilty to support this flag
    }

    @Override
    public String getContainingTableName() {
        return fieldMeta.getTableName();
    }
}
