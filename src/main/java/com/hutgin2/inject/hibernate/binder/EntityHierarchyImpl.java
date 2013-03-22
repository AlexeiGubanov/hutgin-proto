package com.hutgin2.inject.hibernate.binder;

import org.hibernate.metamodel.binding.InheritanceType;
import org.hibernate.metamodel.source.binder.EntityHierarchy;
import org.hibernate.metamodel.source.binder.RootEntitySource;

/**
 * Describes entity hierarchy model for Binder
 */
public class EntityHierarchyImpl implements EntityHierarchy {
    private final RootEntitySourceImpl rootEntitySource;
    private InheritanceType hierarchyInheritanceType = InheritanceType.NO_INHERITANCE;

    public EntityHierarchyImpl(RootEntitySourceImpl rootEntitySource) {
        this.rootEntitySource = rootEntitySource;
    }

    @Override
    public InheritanceType getHierarchyInheritanceType() {
        return hierarchyInheritanceType;
    }

    @Override
    public RootEntitySource getRootEntitySource() {
        return rootEntitySource;
    }
}
