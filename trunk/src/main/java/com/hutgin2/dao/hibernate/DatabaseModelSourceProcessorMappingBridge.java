package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Mappings;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.engine.ResultSetMappingDefinition;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.hibernate.internal.util.ValueHolder;
import org.hibernate.metamodel.SessionFactoryBuilder;
import org.hibernate.metamodel.binding.*;
import org.hibernate.metamodel.domain.Type;
import org.hibernate.metamodel.relational.Database;
import org.hibernate.metamodel.source.MappingDefaults;
import org.hibernate.metamodel.source.MetaAttributeContext;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.TypeResolver;

import java.util.ArrayList;
import java.util.Map;

/**
 * TODO see MetaMappingBinder
 */
public class DatabaseModelSourceProcessorMappingBridge implements MetadataImplementor {
    private Mappings mappings;
    final ArrayList<String> processedEntityNames = new ArrayList<String>();
    private ServiceRegistry registry;
    private final Database database;
    private TypeResolver typeResolver = new TypeResolver();

    public DatabaseModelSourceProcessorMappingBridge(ServiceRegistry registry, Mappings mappings, DatabaseModel model) {
        this.mappings = mappings;
        this.registry = registry;
        this.database = new Database(new DatabaseModelMetadataBuilderImpl.OptionsImpl(registry));
        DatabaseModelSourceProcessor processor = new DatabaseModelSourceProcessor(this, model);
        processor.prepare(null);
        processor.processIndependentMetadata(null);
        processor.processTypeDependentMetadata(null);
        processor.processMappingMetadata(null, processedEntityNames);
        processor.processMappingDependentMetadata(null);
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return registry;
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public TypeResolver getTypeResolver() {
        return typeResolver;
    }

    @Override
    public void addImport(String entityName, String entityName1) {
        mappings.addImport(entityName, entityName1);
    }

    @Override
    public void addEntity(EntityBinding entityBinding) {
    }

    @Override
    public void addCollection(PluralAttributeBinding collectionBinding) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addFetchProfile(FetchProfile profile) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addTypeDefinition(TypeDef typeDef) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addFilterDefinition(FilterDefinition filterDefinition) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addIdGenerator(IdGenerator generator) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerIdentifierGenerator(String name, String clazz) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addNamedNativeQuery(NamedSQLQueryDefinition def) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addNamedQuery(NamedQueryDefinition def) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addResultSetMapping(ResultSetMappingDefinition resultSetMappingDefinition) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setGloballyQuotedIdentifiers(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MetaAttributeContext getGlobalMetaAttributeContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NamingStrategy getNamingStrategy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MappingDefaults getMappingDefaults() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MetadataImplementor getMetadataImplementor() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> Class<T> locateClassByName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Type makeJavaType(String className) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isGloballyQuotedIdentifiers() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ValueHolder<Class<?>> makeClassReference(String className) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String qualifyClassName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IdentifierGeneratorFactory getIdentifierGeneratorFactory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public org.hibernate.type.Type getIdentifierType(String className) throws MappingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIdentifierPropertyName(String className) throws MappingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public org.hibernate.type.Type getReferencedPropertyType(String className, String propertyName) throws MappingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Options getOptions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SessionFactoryBuilder getSessionFactoryBuilder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SessionFactory buildSessionFactory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<EntityBinding> getEntityBindings() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityBinding getEntityBinding(String entityName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityBinding getRootEntityBinding(String entityName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<PluralAttributeBinding> getCollectionBindings() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeDef getTypeDefinition(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<TypeDef> getTypeDefinitions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<FilterDefinition> getFilterDefinitions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<NamedQueryDefinition> getNamedQueryDefinitions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<NamedSQLQueryDefinition> getNamedNativeQueryDefinitions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<ResultSetMappingDefinition> getResultSetMappingDefinitions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<Map.Entry<String, String>> getImports() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<FetchProfile> getFetchProfiles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IdGenerator getIdGenerator(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
