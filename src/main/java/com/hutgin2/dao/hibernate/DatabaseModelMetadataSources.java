package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.metamodel.MetadataBuilder;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.xml.sax.EntityResolver;

public class DatabaseModelMetadataSources extends MetadataSources {

    private final DatabaseModelMetadataBuilderImpl metadataBuilder;


    public DatabaseModelMetadataSources(ServiceRegistry serviceRegistry, DatabaseModel model) {
        super(serviceRegistry);
        this.metadataBuilder = new DatabaseModelMetadataBuilderImpl(this, model);
    }

    public DatabaseModelMetadataSources(ServiceRegistry serviceRegistry, EntityResolver entityResolver, NamingStrategy namingStrategy, DatabaseModel model) {
        super(serviceRegistry, entityResolver, namingStrategy);
        this.metadataBuilder = new DatabaseModelMetadataBuilderImpl(this, model);
    }

    @Override
    public MetadataBuilder getMetadataBuilder() {
        return metadataBuilder;
    }
}
