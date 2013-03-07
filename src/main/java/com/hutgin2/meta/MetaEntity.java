package com.hutgin2.meta;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Entity
@MappedSuperclass
public class MetaEntity {

    /**
     * Flag if entity itself is persistent.
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
