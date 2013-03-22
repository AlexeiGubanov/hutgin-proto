package com.hutgin2.inject.hibernate.hack;

import com.hutgin2.core.meta.DatabaseModel;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.metamodel.MetadataBuilder;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.xml.sax.EntityResolver;

public class DMMetadataSources extends MetadataSources {

    private final DMMetadataBuilderImpl metadataBuilder;


    public DMMetadataSources(ServiceRegistry serviceRegistry, DatabaseModel model) {
        super(serviceRegistry);
        this.metadataBuilder = new DMMetadataBuilderImpl(this, model);
    }

    public DMMetadataSources(ServiceRegistry serviceRegistry, EntityResolver entityResolver, NamingStrategy namingStrategy, DatabaseModel model) {
        super(serviceRegistry, entityResolver, namingStrategy);
        this.metadataBuilder = new DMMetadataBuilderImpl(this, model);
    }

    @Override
    public MetadataBuilder getMetadataBuilder() {
        return metadataBuilder;
    }
}
