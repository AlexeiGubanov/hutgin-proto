package com.hutgin2.dao.hibernate;

import com.hutgin2.dao.hibernate.hack.DMMetadataSources;
import com.hutgin2.export.hbm.Exporter;
import com.hutgin2.meta.DatabaseModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Component
public class EntitySessionFactory {

    @Autowired
    private Properties hibernatePropertiesMain;

    @Autowired
    private DataSource dataSourceMain;

    private SessionFactory sessionFactory;

    public synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("EntitySessionFactory is not configured yet. Please call .init() before.");
        }
        return sessionFactory;
    }

    /**
     * Hibernate 3.6 approach. Unfortunally it used SAXparses which cannot read xml-generated file using JAXB.
     */
    public synchronized void initAsHibernateConfiguration(DatabaseModel model) {

        // export schema into xml using exporter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new Exporter().export(model, outputStream);

        Configuration configuration = new Configuration();
        configuration.addInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(hibernatePropertiesMain)
                .applySetting(Environment.DATASOURCE, dataSourceMain)
                .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * Hibernate 3.6 approach. Unfortunally it used SAXparses which cannot read xml-generated file using JAXB.
     */
    public synchronized void initAsHibernateConfiguration(String resPath) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSourceMain);
        builder.getProperties().putAll(hibernatePropertiesMain);
        builder.addResource(resPath);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(hibernatePropertiesMain)
                .applySetting(Environment.DATASOURCE, dataSourceMain)
                .buildServiceRegistry();
        sessionFactory = builder.buildSessionFactory(serviceRegistry);
    }

    /**
     * Spring LocalSessionFactoryBean approach. Based on hibernate 3 approach, but wrapped with spring functionality.
     * Actual
     */
    public synchronized void init(DatabaseModel model) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSourceMain);
        builder.getProperties().putAll(hibernatePropertiesMain);
        MetaMappingBinder.bindDatabaseModel(model, builder.createMappings(), new HashMap<>());
        sessionFactory = builder.buildSessionFactory();
    }

    /**
     * Hibernate 4 approach: using metadatasorurce processor. Not using Spring
     * Throws NPE when trying to build session factory during retrieving corresponding class for entity (using dynamic-map mode)
     * Waiting for Hibernate 5
     */
    public synchronized void initWithDatabaseModelSourceProcessor(DatabaseModel model) {
        // service registry builder
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(hibernatePropertiesMain);
        serviceRegistryBuilder.applySetting(Environment.DATASOURCE, dataSourceMain);

        // get service registry instance
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
        // construct metadata sources
        MetadataSources metadataSources = new DMMetadataSources(serviceRegistry, model);
        // construct metadata
        Metadata metadata = metadataSources.buildMetadata();
        // finally, construct session factory
        sessionFactory = metadata.buildSessionFactory();
    }

    /**
     * Hibernate 4 approach: using metadata source and JAXB parser.
     * currently it ignore default_entity_mode = dynamic_map
     */
    @Deprecated
    public synchronized void initAsMetamodel(DatabaseModel model) {
        // export schema into xml using exporter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new Exporter().export(model, outputStream);
        try {
            FileOutputStream fos = new FileOutputStream("e:/test.hbm.xml");
            fos.write(outputStream.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // service registry builder
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(hibernatePropertiesMain);
        serviceRegistryBuilder.applySetting(Environment.DATASOURCE, dataSourceMain);

        // get service registry instance
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
        // construct metadata sources
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        // load from source stream
        metadataSources.addInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        // construct metadata
        Metadata metadata = metadataSources.buildMetadata();
        // finally, construct session factory
        sessionFactory = metadata.buildSessionFactory();


    }

    /**
     * Hibernate 4 approach: using metadata source and JAXB parser.
     * currently it ignore default_entity_mode = dynamic_map
     */
    @Deprecated
    public synchronized void initAsMetamodel(String hbmPathRes) {
        // service registry builder
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(hibernatePropertiesMain);
        serviceRegistryBuilder.applySetting(Environment.DATASOURCE, dataSourceMain);

        // get service registry instance
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
        // construct metadata sources
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        // load from source stream
        metadataSources.addResource(hbmPathRes);
        // construct metadata
        Metadata metadata = metadataSources.buildMetadata();
        // finally, construct session factory
        sessionFactory = metadata.buildSessionFactory();


    }
}
