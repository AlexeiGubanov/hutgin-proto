package com.hutgin2.inject.hibernate.binder;

import com.hutgin2.core.meta.ConstraintMeta;
import com.hutgin2.core.meta.ConstraintUQMeta;
import com.hutgin2.core.meta.FieldMeta;
import com.hutgin2.core.meta.TableMeta;
import org.hibernate.AssertionFailure;
import org.hibernate.EntityMode;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.engine.OptimisticLockStyle;
import org.hibernate.internal.jaxb.Origin;
import org.hibernate.metamodel.binding.Caching;
import org.hibernate.metamodel.binding.CustomSQL;
import org.hibernate.metamodel.source.LocalBindingContext;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.metamodel.source.binder.*;

import java.util.*;

/**
 * Describes root entity source for binder.
 * TODO all tableMeta are root sources currently, but it must be changed
 * so it is neccessary to implement EntitySource interface apart from this class
 */
public class RootEntitySourceImpl implements RootEntitySource {

    private final TableMeta tableMeta;
    private MetadataImplementor metadata;
    private LocalBindingContext localBindingContext;

    public RootEntitySourceImpl(TableMeta tableMeta, MetadataImplementor metadata, LocalBindingContext bindingContext) {
        this.tableMeta = tableMeta;
        this.metadata = metadata;
        this.localBindingContext = bindingContext;
    }

    @Override
    public IdentifierSource getIdentifierSource() {
        // list table field to find PK
        for (FieldMeta fieldMeta : tableMeta.getFields()) {
            if (fieldMeta.isPrimaryKey()) {
                return new SimpleIdentifierSourceImpl(fieldMeta, metadata);
                // TODO check if this columns must be excluded from attributeSources()
            }
        }
        //TODO support composites
//        throw new NotYetImplementedException( "Composed ids must still be implemented." );
//        throw new NotYetImplementedException( "Embedded ids must still be implemented." );
//        throw new NotYetImplementedException("Composite PK in table constraints not yet implemented");
        throw new AssertionFailure("The root entity needs to specify an identifier");
    }

    @Override
    public SingularAttributeSource getVersioningAttributeSource() {
        // TODO must return version attribute source, see org.hibernate.metamodel.source.hbm.RootEntitySourceImpl.getVersioningAttributeSource
        return null;
    }

    @Override
    public DiscriminatorSource getDiscriminatorSource() {
        // TODO must return discriminator attrubute source
        return null;
    }

    @Override
    public EntityMode getEntityMode() {
        // TODO depends on tableMeta type - if implementing class is presented, must be POJO
        return EntityMode.MAP;
    }

    @Override
    public boolean isMutable() {
        return !tableMeta.isVirtual();  // TODO check possibility to support this flag with others properties
    }

    @Override
    public boolean isExplicitPolymorphism() {
        return false;  //by default olymorphismType.IMPLICIT (false), TODO check to support
    }

    @Override
    public String getWhere() {
        return null;  // TODO check to support
    }

    @Override
    public String getRowId() {
        return null;  //TODO check to support
    }

    @Override
    public OptimisticLockStyle getOptimisticLockStyle() {
        return OptimisticLockStyle.NONE; // TODO against versioning or additional property
    }

    @Override
    public Caching getCaching() {
        return null; // TODO  check to support
    }

    @Override
    public Origin getOrigin() {
        return this.localBindingContext.getOrigin();
    }

    @Override
    public LocalBindingContext getLocalBindingContext() {
        return this.localBindingContext;
    }

    @Override
    public String getEntityName() {
        return tableMeta.getName();
    }

    @Override
    public String getClassName() {
        return null;  // TODO depends on tableMeta type - if implementing class is presented, must be its name
    }

    @Override
    public String getJpaEntityName() {
        return tableMeta.getName();
    }

    @Override
    public TableSource getPrimaryTable() {
        return new TableSource() {
            @Override
            public String getExplicitSchemaName() {
                return null;
//                return tableMeta.getSchema();//TODO support
            }

            @Override
            public String getExplicitCatalogName() {
                return null;
//                return tableMeta.getCatalog();//TODO support
            }

            @Override
            public String getExplicitTableName() {
                return tableMeta.getName();
            }

            @Override
            public String getLogicalName() {
                // logical name for the primary table is null
                return null;
            }
        };
    }

    @Override
    public Iterable<TableSource> getSecondaryTables() {
        return Collections.emptySet(); //TODO check designation
    }

    @Override
    public String getCustomTuplizerClassName() {
        return null;  //TODO check designation tuplizer
    }

    @Override
    public String getCustomPersisterClassName() {
        return null;  //TODO check designation
    }

    @Override
    public boolean isLazy() {
        return false;  //TODO check support
    }

    @Override
    public String getProxy() {
        return null;  // TODO check support
    }

    @Override
    public int getBatchSize() {
        return -1;  //TODO same as getProxy
    }

    @Override
    public boolean isAbstract() {
        return tableMeta.isVirtual();
    }

    @Override
    public boolean isDynamicInsert() {
        return false;  //TODO check support
    }

    @Override
    public boolean isDynamicUpdate() {
        return false;   //TODO check support
    }

    @Override
    public boolean isSelectBeforeUpdate() {
        return false;  //TODO check designation
    }

    @Override
    public String getCustomLoaderName() {
        return null;  //TODO check support
    }

    @Override
    public CustomSQL getCustomSqlInsert() {
        return null;  //TODO check support
    }

    @Override
    public CustomSQL getCustomSqlUpdate() {
        return null;  //TODO check support
    }

    @Override
    public CustomSQL getCustomSqlDelete() {
        return null;  //TODO check support
    }

    @Override
    public List<String> getSynchronizedTableNames() {
        return Collections.emptyList(); // TODO check designation
    }

    @Override
    public Iterable<MetaAttributeSource> metaAttributes() {
        return Collections.emptySet();  // TODO check designation
    }

    @Override
    public String getDiscriminatorMatchValue() {
        return null;  //TODO check support
    }

    @Override
    public Iterable<ConstraintSource> getConstraints() {
        Set<ConstraintSource> result = new HashSet<>();
        for (ConstraintMeta constraint : tableMeta.getConstraints()) {
            if (constraint instanceof ConstraintUQMeta) {
                result.add(new UniqueConstraintSourceImpl((ConstraintUQMeta) constraint));
            }
        }
        return result;
    }

    @Override
    public List<JpaCallbackClass> getJpaCallbackClasses() {
        return Collections.emptyList(); // TODO check designation
    }

    @Override
    public String getPath() {
        return tableMeta.getName();
    }

    @Override
    public Iterable<AttributeSource> attributeSources() {
        List<AttributeSource> attributeSources = new ArrayList<AttributeSource>();
        for (FieldMeta field : tableMeta.getFields()) {
            switch (field.getAssociationType()) {
                case NONE:
                    attributeSources.add(new SingularAttributeSourceImpl(field));
                    // TODO support components: depends on field type class
                    break;
                case ONE_TO_ONE:
                case MANY_TO_ONE:
                    attributeSources.add(new ToOneAttributeSourceImpl(field));
                    break;
                case ONE_TO_MANY:
                case MANY_TO_MANY:
                    throw new NotYetImplementedException("*ToMany properties are not yet supported");
//                    break;
                    // todo what about bag,set,list,idbag,map? see hbm.AbstractEntitySourceImpl
                    // it must depends on *ToMany field class type!
            }
        }
        return attributeSources;
    }

    private final Set<SubclassEntitySource> subclassEntitySources = new HashSet<SubclassEntitySource>();
    ;

    @Override
    public void add(SubclassEntitySource subclassEntitySource) {
        subclassEntitySource.add(subclassEntitySource);
    }

    @Override
    public Iterable<SubclassEntitySource> subclassEntitySources() {
        return subclassEntitySources;
    }
}
