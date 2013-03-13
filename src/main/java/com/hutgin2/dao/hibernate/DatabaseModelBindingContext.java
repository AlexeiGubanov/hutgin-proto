package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.internal.jaxb.Origin;
import org.hibernate.internal.jaxb.SourceType;
import org.hibernate.internal.util.ValueHolder;
import org.hibernate.metamodel.domain.Type;
import org.hibernate.metamodel.source.LocalBindingContext;
import org.hibernate.metamodel.source.MappingDefaults;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class DatabaseModelBindingContext implements LocalBindingContext {
    private DatabaseModel model;
    private MetadataImplementor metadata;
    private Origin origin;

    public DatabaseModelBindingContext(DatabaseModel model, MetadataImplementor metadata) {
        this.model = model;
        this.metadata = metadata;
        this.origin = new Origin(SourceType.OTHER, "META"); // TODO provide META database info instead "META"

    }

    @Override
    public Origin getOrigin() {
        return this.origin;
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return metadata.getServiceRegistry();
    }

    @Override
    public NamingStrategy getNamingStrategy() {
        return metadata.getNamingStrategy();
    }

    @Override
    public MappingDefaults getMappingDefaults() {
        return metadata.getMappingDefaults();
    }

    @Override
    public MetadataImplementor getMetadataImplementor() {
        return metadata.getMetadataImplementor();
    }

    @Override
    public <T> Class<T> locateClassByName(String name) {
        return metadata.locateClassByName(name);
    }

    // cache
    private Map<String, Type> nameToJavaTypeMap = new HashMap<String, Type>();

    @Override
    public Type makeJavaType(String className) {
        Type javaType = nameToJavaTypeMap.get(className);
        if (javaType == null) {
            javaType = metadata.makeJavaType(className);
            nameToJavaTypeMap.put(className, javaType);
        }
        return javaType;
    }

    @Override
    public boolean isGloballyQuotedIdentifiers() {
        return metadata.isGloballyQuotedIdentifiers();
    }

    @Override
    public ValueHolder<Class<?>> makeClassReference(String className) {
        return metadata.makeClassReference(className);
    }

    @Override
    public String qualifyClassName(String name) {
        return metadata.qualifyClassName(name);
    }
}
