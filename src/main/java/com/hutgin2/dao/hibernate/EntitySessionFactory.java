package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
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
        LocalSessionFactoryBuilder configuration = new LocalSessionFactoryBuilder(dataSource);
        configuration.setProperties(hibernateProperties);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            new Exporter().export(model, "e:/test1.hbm.xml");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        configuration.addInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        configuration.addResource("testData/hbm/sample.hbm.xml");
        sessionFactory = configuration.buildSessionFactory();

    }
}
