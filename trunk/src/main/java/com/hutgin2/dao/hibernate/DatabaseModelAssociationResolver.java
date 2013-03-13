package com.hutgin2.dao.hibernate;

import org.hibernate.metamodel.binding.AttributeBinding;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.binding.SingularAssociationAttributeBinding;
import org.hibernate.metamodel.source.MetadataImplementor;

/**
 * Kind of hack
 * {@link org.hibernate.metamodel.source.internal.AssociationResolver}
 * TODO review
 */
public class DatabaseModelAssociationResolver {
    private final MetadataImplementor metadata;

    DatabaseModelAssociationResolver(MetadataImplementor metadata) {
        this.metadata = metadata;
    }

    void resolve() {
        for (EntityBinding entityBinding : metadata.getEntityBindings()) {
            for (SingularAssociationAttributeBinding attributeBinding : entityBinding.getEntityReferencingAttributeBindings()) {
                resolve(attributeBinding);
            }
        }
    }

    private void resolve(SingularAssociationAttributeBinding attributeBinding) {
        if (attributeBinding.getReferencedEntityName() == null) {
            throw new IllegalArgumentException(
                    "attributeBinding has null entityName: " + attributeBinding.getAttribute().getName()
            );
        }
        EntityBinding entityBinding = metadata.getEntityBinding(attributeBinding.getReferencedEntityName());
        if (entityBinding == null) {
            throw new org.hibernate.MappingException(
                    String.format(
                            "Attribute [%s] refers to unknown entity: [%s]",
                            attributeBinding.getAttribute().getName(),
                            attributeBinding.getReferencedEntityName()
                    )
            );
        }
        AttributeBinding referencedAttributeBinding =
                attributeBinding.isPropertyReference() ?
                        entityBinding.locateAttributeBinding(attributeBinding.getReferencedAttributeName()) :
                        entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding();
        if (referencedAttributeBinding == null) {
            // TODO: does attribute name include path w/ entity name?
            throw new org.hibernate.MappingException(
                    String.format(
                            "Attribute [%s] refers to unknown attribute: [%s]",
                            attributeBinding.getAttribute().getName(),
                            attributeBinding.getReferencedEntityName()
                    )
            );
        }
        attributeBinding.resolveReference(referencedAttributeBinding);
        referencedAttributeBinding.addEntityReferencingAttributeBinding(attributeBinding);
    }
}
