package com.hutgin2.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PK")
public class ConstraintPKMeta extends ConstraintMeta {
}
