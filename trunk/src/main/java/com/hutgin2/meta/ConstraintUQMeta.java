package com.hutgin2.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UQ")
public class ConstraintUQMeta extends ConstraintMeta {
}
