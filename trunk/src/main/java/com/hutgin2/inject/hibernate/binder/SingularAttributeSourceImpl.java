package com.hutgin2.inject.hibernate.binder;

import com.hutgin2.core.meta.FieldMeta;
import com.hutgin2.core.meta.ValueGenerationStrategy;
import org.hibernate.mapping.PropertyGeneration;
import org.hibernate.metamodel.source.binder.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SingularAttributeSourceImpl implements SingularAttributeSource {

    private final FieldMeta fieldMeta;

    private ExplicitHibernateTypeSource typeSource;

    public SingularAttributeSourceImpl(FieldMeta fieldMeta) {
        this.fieldMeta = fieldMeta;
        this.typeSource = new ExplicitHibernateTypeSource() {
            @Override
            public String getName() {
                return SingularAttributeSourceImpl.this.fieldMeta.getType().getName();
            }

            @Override
            public Map<String, String> getParameters() {
                return null; // TODO chech allowed type parameters
            }
        };
    }

    @Override
    public boolean isVirtualAttribute() {
        return fieldMeta.isVirtual();
    }

    @Override
    public SingularAttributeNature getNature() {
        return SingularAttributeNature.BASIC;
    }

    @Override
    public boolean isInsertable() {
        // TODO add support
        return !fieldMeta.isVirtual() && fieldMeta.getInsertGenerationStrategy() != ValueGenerationStrategy.DATABASE;
    }

    @Override
    public boolean isUpdatable() {
        // TODO add support
        return !fieldMeta.isVirtual() && fieldMeta.getInsertGenerationStrategy() != ValueGenerationStrategy.DATABASE;
    }

    @Override
    public PropertyGeneration getGeneration() {
        if (fieldMeta.getInsertGenerationStrategy() == ValueGenerationStrategy.DATABASE
                && fieldMeta.getUpdateGenerationStrategy() == ValueGenerationStrategy.DATABASE) {
            return PropertyGeneration.ALWAYS;
        } else if (fieldMeta.getInsertGenerationStrategy() == ValueGenerationStrategy.DATABASE) {
            return PropertyGeneration.INSERT;
        } else {
            //TODO PropertyGeneration don't support only generation on update, leave as never
            return PropertyGeneration.NEVER;
        }
    }

    @Override
    public boolean isLazy() {
        return false; // TODO check laziness support
    }

    @Override
    public String getName() {
        return fieldMeta.getName(); // attrubute name!
    }

    @Override
    public boolean isSingular() {
        return true;
    }

    @Override
    public ExplicitHibernateTypeSource getTypeInformation() {
        return typeSource;
    }

    /**
     * The access type for this property. At the moment this is either 'field' or 'property', but Hibernate
     * also allows custom named accessors (see {@link org.hibernate.property.PropertyAccessorFactory}).
     */
    @Override
    public String getPropertyAccessorName() {
        return null; // null means basic property accessor strategy, see PropertyAccessorFactory for details
    }

    @Override
    public boolean isIncludedInOptimisticLocking() {
        return true;  //TODO check possibilty to support this flag, by default true
    }

    @Override
    public Iterable<MetaAttributeSource> metaAttributes() {
        return Collections.emptySet(); // empty list, TODO check designation of MetaAttributeSource
    }

    @Override
    public boolean areValuesIncludedInInsertByDefault() {
        return true;  // TODO check against virtual and database,check possibilty to support this flag
    }

    @Override
    public boolean areValuesIncludedInUpdateByDefault() {
        return true;  // TODO check against virtual and database,check possibilty to support this flag
    }

    @Override
    public boolean areValuesNullableByDefault() {
        return !fieldMeta.isRequired();
    }

    @Override
    public List<RelationalValueSource> relationalValueSources() {
        // TODO currently is always as column
        // but further it can be formula - means field is not persistable but calculated with formula (or formula implementation)
        List<RelationalValueSource> valueSources = new ArrayList<RelationalValueSource>();
        valueSources.add(new ColumnSourceImpl(fieldMeta));
        return valueSources;
    }

    public FieldMeta getFieldMeta() {
        return fieldMeta;
    }
}
