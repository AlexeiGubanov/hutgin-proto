package com.hutgin2.core.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("IDX")
public class ConstraintIDXMeta extends ConstraintMeta {

    private boolean uniq;

    public boolean isUniq() {
        return uniq;
    }

    public void setUniq(boolean uniq) {
        this.uniq = uniq;
    }
}
