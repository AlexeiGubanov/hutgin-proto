package com.hutgin.dbsupport.meta.constraint;

import com.hutgin.dbsupport.meta.Column;

public class PrimaryKeyConstraint extends Constraint {

    public PrimaryKeyConstraint(String name, Column... columns) {
        super(name, columns);
    }
}
