package com.hutgin2.dao.hibernate.binder;

import com.hutgin2.meta.TableMeta;
import org.hibernate.EntityMode;
import org.hibernate.engine.OptimisticLockStyle;
import org.hibernate.internal.jaxb.Origin;
import org.hibernate.metamodel.binding.Caching;
import org.hibernate.metamodel.binding.CustomSQL;
import org.hibernate.metamodel.source.LocalBindingContext;
import org.hibernate.metamodel.source.binder.*;

import java.util.List;

/**
 * Describes root entity source for binder.
 * TODO all tableMeta are root sources currently, but it must be changed
 * TODO so it is neccessary to implement EntitySource interface apart from this class
 */
public class RootEntitySourceImpl implements RootEntitySource {

    private final TableMeta tableMeta;

    public RootEntitySourceImpl(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    @Override
    public IdentifierSource getIdentifierSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SingularAttributeSource getVersioningAttributeSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DiscriminatorSource getDiscriminatorSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityMode getEntityMode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isMutable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isExplicitPolymorphism() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getWhere() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getRowId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OptimisticLockStyle getOptimisticLockStyle() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Caching getCaching() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Origin getOrigin() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LocalBindingContext getLocalBindingContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEntityName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getClassName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getJpaEntityName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TableSource getPrimaryTable() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<TableSource> getSecondaryTables() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCustomTuplizerClassName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCustomPersisterClassName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isLazy() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getProxy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getBatchSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAbstract() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isDynamicInsert() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isDynamicUpdate() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSelectBeforeUpdate() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCustomLoaderName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CustomSQL getCustomSqlInsert() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CustomSQL getCustomSqlUpdate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CustomSQL getCustomSqlDelete() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getSynchronizedTableNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<MetaAttributeSource> metaAttributes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDiscriminatorMatchValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<ConstraintSource> getConstraints() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<JpaCallbackClass> getJpaCallbackClasses() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<AttributeSource> attributeSources() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(SubclassEntitySource subclassEntitySource) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<SubclassEntitySource> subclassEntitySources() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
