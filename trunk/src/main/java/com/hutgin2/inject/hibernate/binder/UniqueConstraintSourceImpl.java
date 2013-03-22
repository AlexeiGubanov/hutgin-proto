package com.hutgin2.inject.hibernate.binder;

import com.hutgin2.core.meta.ConstraintUQMeta;
import com.hutgin2.core.meta.FieldMeta;
import org.hibernate.metamodel.source.binder.UniqueConstraintSource;

import java.util.ArrayList;
import java.util.List;

public class UniqueConstraintSourceImpl implements UniqueConstraintSource {

    private ConstraintUQMeta uqMeta;

    public UniqueConstraintSourceImpl(ConstraintUQMeta uqMeta) {
        this.uqMeta = uqMeta;
    }

    @Override
    public String name() {
        return uqMeta.getName();
    }

    @Override
    public String getTableName() {
        return uqMeta.getTableName();
    }

    @Override
    public Iterable<String> columnNames() {
        List<String> columns = new ArrayList<>();
        for (FieldMeta field : uqMeta.getFields()) {
            columns.add(field.getName());
        }
        return columns;
    }
}
