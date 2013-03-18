package com.hutgin2.dao.hibernate;

import com.hutgin2.dao.hibernate.binder.EntityHierarchyImpl;
import com.hutgin2.dao.hibernate.binder.RootEntitySourceImpl;
import com.hutgin2.dao.hibernate.hack.DMBinder;
import com.hutgin2.meta.DatabaseModel;
import com.hutgin2.meta.TableMeta;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.metamodel.source.LocalBindingContext;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.metamodel.source.MetadataSourceProcessor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModelSourceProcessor implements MetadataSourceProcessor {

    private MetadataImplementor metadata;
    private DatabaseModel model;
    private LocalBindingContext bindingContext;

    private List<EntityHierarchyImpl> entityHierarchies = new ArrayList<>();


    public DatabaseModelSourceProcessor(MetadataImplementor metadata, DatabaseModel model) {
        this.metadata = metadata;
        this.model = model;
        this.bindingContext = new DatabaseModelBindingContext(model, metadata);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void prepare(MetadataSources sources) {
//        final HierarchyBuilder hierarchyBuilder = new HierarchyBuilder();
        // NOTE all classes are ROOT!
        for (TableMeta tableMeta : model.getTables()) {
            RootEntitySourceImpl root = new RootEntitySourceImpl(tableMeta, metadata, bindingContext);
            entityHierarchies.add(new EntityHierarchyImpl(root));
        }

//        this.entityHierarchies = hierarchyBuilder.groupEntityHierarchies();
    }

    @Override
    public void processIndependentMetadata(MetadataSources sources) {

        //HBM 		HibernateMappingProcessor.processDatabaseObjectDefinitions();
        //HBM 		HibernateMappingProcessor.processTypeDefinitions();
        // ANNOTATIONS: TypeDefBinder.bind(bindingContext);
    }

    @Override
    public void processTypeDependentMetadata(MetadataSources sources) {
        //HBM HibernateMappingProcessor.processFilterDefinitions();
        //HBM HibernateMappingProcessor.processIdentifierGenerators();
        //ANNOTATIONS AnnotationMetadataSourceProcessorImpl.IdGeneratorBinder.bind(bindingContext);
    }

    @Override
    public void processMappingMetadata(MetadataSources sources, List<String> processedEntityNames) {
        DMBinder binder = new DMBinder(metadata, processedEntityNames);
        for (EntityHierarchyImpl entityHierarchy : entityHierarchies) {
            binder.processEntityHierarchy(entityHierarchy);
        }
    }

    @Override
    public void processMappingDependentMetadata(MetadataSources sources) {
//HBM         HibernateMappingProcessor.processFetchProfiles();
//            HibernateMappingProcessor.processImports();
//            HibernateMappingProcessor.processResultSetMappings();
//            HibernateMappingProcessor.processNamedQueries();
//        }
        //ANNOTATIONS:
//        TableBinder.bind(bindingContext);
//        FetchProfileBinder.bind(bindingContext);
//        QueryBinder.bind(bindingContext);
//        FilterDefBinder.bind( bindingContext );
    }
}
