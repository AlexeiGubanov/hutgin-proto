package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.SessionFactory;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Component
public class EntitySessionFactory {

    @Autowired
    private Properties hibernateProperties;

    @Autowired
    private DataSource dataSource;

    private SessionFactory sessionFactory;

    public synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("EntitySessionFactory is not configured yet. Please call .init() before.");
        }
        return sessionFactory;
    }

    public synchronized void init(DatabaseModel model) {
//        LocalSessionFactoryBuilder configuration = new LocalSessionFactoryBuilder(dataSource);
//        configuration.setProperties(hibernateProperties);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            new Exporter().export(model, "e:/test1.hbm.xml");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(hibernateProperties);
//        serviceRegistryBuilder.applySetting(Environment.DATASOURCE, dataSource);
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addFile("e:/test1.hbm.xml");

        Metadata metadata = metadataSources.buildMetadata();
        sessionFactory = metadata.buildSessionFactory();


    }
}
