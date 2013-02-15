package com.hutgin.dbsupport.meta.constraint;

import com.hutgin.dbsupport.meta.Column;

public class UniqueConstraint extends Constraint {
    public UniqueConstraint(String name, Column... columns) {
        super(name, columns);
    }
}
