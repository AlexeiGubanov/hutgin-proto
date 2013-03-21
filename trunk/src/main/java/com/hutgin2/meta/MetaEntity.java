package com.hutgin2.meta;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class MetaEntity {

    /**
     * Flag if entity itself (as a database record) is persistent.
     * User to add any fantom objects into memory model
     */
    private boolean persistable = true;

    @Transient
    public boolean isPersistable() {
        return persistable;
    }

    public void setPersistable(boolean persistable) {
        this.persistable = persistable;
    }
}
