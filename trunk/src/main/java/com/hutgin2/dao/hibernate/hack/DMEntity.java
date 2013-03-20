package com.hutgin2.dao.hibernate.hack;

import org.hibernate.internal.util.ValueHolder;
import org.hibernate.metamodel.domain.Entity;
import org.hibernate.metamodel.domain.Hierarchical;

/**
 * DMBinder:153
 */
public class DMEntity extends Entity {
    public DMEntity(String entityName, String className, ValueHolder<Class<?>> classReference, Hierarchical superType) {
        super(entityName, className, classReference, superType);
    }

    @Override
    public Class<?> getClassReference() {
        if (getClassReferenceUnresolved() == null)
            return null;
        return super.getClassReference();
    }
}
