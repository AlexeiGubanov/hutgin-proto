package com.hutgin2.dao.hibernate.binder;

import com.hutgin2.meta.FieldMeta;
import org.hibernate.FetchMode;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.metamodel.source.binder.SingularAttributeNature;
import org.hibernate.metamodel.source.binder.ToOneAttributeSource;

import java.util.Collections;

public class ToOneAttributeSourceImpl extends SingularAttributeSourceImpl implements ToOneAttributeSource {

    public ToOneAttributeSourceImpl(FieldMeta fieldMeta) {
        super(fieldMeta);
    }

    @Override
    public SingularAttributeNature getNature() {
        return SingularAttributeNature.MANY_TO_ONE;
    }

    @Override
    public String getReferencedEntityName() {
        return getFieldMeta().getFieldRef().getTableName();
    }

    @Override
    public String getReferencedEntityAttributeName() {
        return getFieldMeta().getFieldRef().getName();
    }

    @Override
    public Iterable<CascadeStyle> getCascadeStyles() {
        return Collections.emptyList(); //TODO check possible values, see hbm.ManyToOneAttributeSourceImpl
    }

    @Override
    public FetchMode getFetchMode() {
        return FetchMode.JOIN; //TODO check support, see hbm.ManyToOneAttributeSourceImpl
    }

    @Override
    public FetchTiming getFetchTiming() {
        // todo : implement?
        return FetchTiming.IMMEDIATE;
    }

    @Override
    public FetchStyle getFetchStyle() {
        // todo : implement?
        return FetchStyle.JOIN;
    }


}
