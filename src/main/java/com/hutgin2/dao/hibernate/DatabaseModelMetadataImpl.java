package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.metamodel.source.internal.MetadataImpl;

public class DatabaseModelMetadataImpl extends MetadataImpl {

    public DatabaseModelMetadataImpl(MetadataSources metadataSources, Options options, DatabaseModel model) {
        super(metadataSources, options);
        DatabaseModelSourceProcessor processor = new DatabaseModelSourceProcessor(this, model);
    }
}
