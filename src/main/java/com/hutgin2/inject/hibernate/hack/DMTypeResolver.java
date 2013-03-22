package com.hutgin2.inject.hibernate.hack;

import org.hibernate.AssertionFailure;
import org.hibernate.metamodel.binding.*;
import org.hibernate.metamodel.domain.SingularAttribute;
import org.hibernate.metamodel.relational.Datatype;
import org.hibernate.metamodel.relational.SimpleValue;
import org.hibernate.metamodel.relational.Value;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;

import java.util.Properties;

/**
 * Kind of hack, fully consists of {@link org.hibernate.metamodel.source.internal.HibernateTypeResolver}
 * <p/>
 * TODO review
 */
public class DMTypeResolver {
    private final MetadataImplementor metadata;

    DMTypeResolver(MetadataImplementor metadata) {
        this.metadata = metadata;
    }

    void resolve() {
        for (EntityBinding entityBinding : metadata.getEntityBindings()) {
            if (entityBinding.getHierarchyDetails().getEntityDiscriminator() != null) {
                resolveDiscriminatorTypeInformation(entityBinding.getHierarchyDetails().getEntityDiscriminator());
            }
            for (AttributeBinding attributeBinding : entityBinding.attributeBindings()) {
                if (SingularAttributeBinding.class.isInstance(attributeBinding)) {
                    resolveSingularAttributeTypeInformation(
                            SingularAttributeBinding.class.cast(attributeBinding)
                    );
                } else if (AbstractPluralAttributeBinding.class.isInstance(attributeBinding)) {
                    resolvePluralAttributeTypeInformation(
                            AbstractPluralAttributeBinding.class.cast(attributeBinding)
                    );
                } else {
                    throw new AssertionFailure("Unknown type of AttributeBinding: " + attributeBinding.getClass().getName());
                }
            }
        }
    }

    // perform any needed type resolutions for discriminator
    private void resolveDiscriminatorTypeInformation(EntityDiscriminator discriminator) {
        // perform any needed type resolutions for discriminator
        Type resolvedHibernateType = determineSingularTypeFromDescriptor(discriminator.getExplicitHibernateTypeDescriptor());
        if (resolvedHibernateType != null) {
            pushHibernateTypeInformationDownIfNeeded(
                    discriminator.getExplicitHibernateTypeDescriptor(),
                    discriminator.getBoundValue(),
                    resolvedHibernateType
            );
        }
    }

    private Type determineSingularTypeFromDescriptor(HibernateTypeDescriptor hibernateTypeDescriptor) {
        if (hibernateTypeDescriptor.getResolvedTypeMapping() != null) {
            return hibernateTypeDescriptor.getResolvedTypeMapping();
        }
        String typeName = determineTypeName(hibernateTypeDescriptor);
        Properties typeParameters = getTypeParameters(hibernateTypeDescriptor);
        return getHeuristicType(typeName, typeParameters);
    }

    private static String determineTypeName(HibernateTypeDescriptor hibernateTypeDescriptor) {
        return hibernateTypeDescriptor.getExplicitTypeName() != null
                ? hibernateTypeDescriptor.getExplicitTypeName()
                : hibernateTypeDescriptor.getJavaTypeName();
    }

    private static Properties getTypeParameters(HibernateTypeDescriptor hibernateTypeDescriptor) {
        Properties typeParameters = new Properties();
        if (hibernateTypeDescriptor.getTypeParameters() != null) {
            typeParameters.putAll(hibernateTypeDescriptor.getTypeParameters());
        }
        return typeParameters;
    }

    // perform any needed type resolutions for SingularAttributeBinding
    private void resolveSingularAttributeTypeInformation(SingularAttributeBinding attributeBinding) {
        if (attributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping() != null) {
            return;
        }
        // we can determine the Hibernate Type if either:
        // 		1) the user explicitly named a Type in a HibernateTypeDescriptor
        // 		2) we know the java type of the attribute
        Type resolvedType;
        resolvedType = determineSingularTypeFromDescriptor(attributeBinding.getHibernateTypeDescriptor());
        if (resolvedType == null) {
            if (!attributeBinding.getAttribute().isSingular()) {
                throw new AssertionFailure("SingularAttributeBinding object has a plural attribute: " + attributeBinding.getAttribute().getName());
            }
            final SingularAttribute singularAttribute = (SingularAttribute) attributeBinding.getAttribute();
            if (singularAttribute.getSingularAttributeType() != null) {
                resolvedType = getHeuristicType(
                        singularAttribute.getSingularAttributeType().getClassName(), new Properties()
                );
            }
        }
        if (resolvedType != null) {
            pushHibernateTypeInformationDownIfNeeded(attributeBinding, resolvedType);
        }
    }

    // perform any needed type resolutions for PluralAttributeBinding
    private void resolvePluralAttributeTypeInformation(AbstractPluralAttributeBinding attributeBinding) {
        if (attributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping() != null) {
            return;
        }
        Type resolvedType;
        // do NOT look at java type...
        //String typeName = determineTypeName( attributeBinding.getHibernateTypeDescriptor() );
        String typeName = attributeBinding.getHibernateTypeDescriptor().getExplicitTypeName();
        if (typeName != null) {
            resolvedType =
                    metadata.getTypeResolver()
                            .getTypeFactory()
                            .customCollection(
                                    typeName,
                                    getTypeParameters(attributeBinding.getHibernateTypeDescriptor()),
                                    attributeBinding.getAttribute().getName(),
                                    attributeBinding.getReferencedPropertyName()
                            );
        } else {
            resolvedType = determineDefaultCollectionInformation(attributeBinding);
        }
        if (resolvedType != null) {
            pushHibernateTypeInformationDownIfNeeded(
                    attributeBinding.getHibernateTypeDescriptor(),
                    null,
                    resolvedType);
        }
        resolveCollectionElementTypeInformation(attributeBinding.getCollectionElement());
    }

    private Type determineDefaultCollectionInformation(AbstractPluralAttributeBinding attributeBinding) {
        final TypeFactory typeFactory = metadata.getTypeResolver().getTypeFactory();
        switch (attributeBinding.getAttribute().getNature()) {
            case SET: {
                return typeFactory.set(
                        attributeBinding.getAttribute().getName(),
                        attributeBinding.getReferencedPropertyName()
                );
            }
            case BAG: {
                return typeFactory.bag(
                        attributeBinding.getAttribute().getName(),
                        attributeBinding.getReferencedPropertyName()
                );
            }
            default: {
                throw new UnsupportedOperationException(
                        "Collection type not supported yet:" + attributeBinding.getAttribute().getNature()
                );
            }
        }
    }

    private void resolveCollectionElementTypeInformation(AbstractCollectionElement collectionElement) {
        switch (collectionElement.getCollectionElementNature()) {
            case BASIC: {
                resolveBasicCollectionElement(BasicCollectionElement.class.cast(collectionElement));
                break;
            }
            case COMPOSITE:
            case ONE_TO_MANY:
            case MANY_TO_MANY:
            case MANY_TO_ANY: {
                throw new UnsupportedOperationException("Collection element nature not supported yet: " + collectionElement.getCollectionElementNature());
            }
            default: {
                throw new AssertionFailure("Unknown collection element nature : " + collectionElement.getCollectionElementNature());
            }
        }
    }

    private void resolveBasicCollectionElement(BasicCollectionElement basicCollectionElement) {
        Type resolvedHibernateType = determineSingularTypeFromDescriptor(basicCollectionElement.getHibernateTypeDescriptor());
        if (resolvedHibernateType != null) {
            pushHibernateTypeInformationDownIfNeeded(
                    basicCollectionElement.getHibernateTypeDescriptor(),
                    basicCollectionElement.getElementValue(),
                    resolvedHibernateType
            );
        }
    }

    private Type getHeuristicType(String typeName, Properties typeParameters) {
        if (typeName != null) {
            try {
                return metadata.getTypeResolver().heuristicType(typeName, typeParameters);
            } catch (Exception ignore) {
            }
        }

        return null;
    }

    private void pushHibernateTypeInformationDownIfNeeded(SingularAttributeBinding attributeBinding, Type resolvedHibernateType) {

        final HibernateTypeDescriptor hibernateTypeDescriptor = attributeBinding.getHibernateTypeDescriptor();
        final SingularAttribute singularAttribute = SingularAttribute.class.cast(attributeBinding.getAttribute());
        final Value value = attributeBinding.getValue();
        if (!singularAttribute.isTypeResolved() && hibernateTypeDescriptor.getJavaTypeName() != null) {
            singularAttribute.resolveType(metadata.makeJavaType(hibernateTypeDescriptor.getJavaTypeName()));
        }

        // sql type information ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        this.pushHibernateTypeInformationDownIfNeeded(
                hibernateTypeDescriptor, value, resolvedHibernateType
        );
    }

    private void pushHibernateTypeInformationDownIfNeeded(
            HibernateTypeDescriptor hibernateTypeDescriptor,
            Value value,
            Type resolvedHibernateType) {
        if (resolvedHibernateType == null) {
            return;
        }
        if (hibernateTypeDescriptor.getResolvedTypeMapping() == null) {
            hibernateTypeDescriptor.setResolvedTypeMapping(resolvedHibernateType);
        }

        // java type information ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        if (hibernateTypeDescriptor.getJavaTypeName() == null) {
            hibernateTypeDescriptor.setJavaTypeName(resolvedHibernateType.getReturnedClass().getName());
        }

        // todo : this can be made a lot smarter, but for now this will suffice.  currently we only handle single value bindings

        if (SimpleValue.class.isInstance(value)) {
            SimpleValue simpleValue = (SimpleValue) value;
            if (simpleValue.getDatatype() == null) {
                simpleValue.setDatatype(
                        new Datatype(
                                resolvedHibernateType.sqlTypes(metadata)[0],
                                resolvedHibernateType.getName(),
                                resolvedHibernateType.getReturnedClass()
                        )
                );
            }
        }
    }
}
