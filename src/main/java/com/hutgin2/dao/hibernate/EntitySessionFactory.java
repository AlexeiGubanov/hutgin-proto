package com.hutgin2.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import java.util.Properties;

@Configuration
public class EntitySessionFactory {

    @Autowired
    private Properties hibernateProperties;

    //    @Autowired
    private DriverManagerDataSource dataSource;

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory() {
        //TODO make as singleton
        dataSource = new DriverManagerDataSource(hibernateProperties.getProperty("hibernate.connection.url"),
                hibernateProperties.getProperty("hibernate.connection.username"),
                hibernateProperties.getProperty("hibernate.connection.password"));
        dataSource.setDriverClassName(hibernateProperties.getProperty("hibernate.connection.driver_class"));
        LocalSessionFactoryBuilder configuration = new LocalSessionFactoryBuilder(dataSource);
        configuration.setProperties(hibernateProperties);

        configuration.addResource("testData/hbm/sample.hbm.xml");


//        Mappings mappings = configuration.createMappings();
//        Table t = mappings.addTable(null, null, "table1", null, false);
//        mappings.addTableBinding(null, null, "table1", "table1", null);
//        Column col = new Column();
//        col.setName("col1");
//        col.setLength(255);
//        col.setNullable(false);
//        col.setSqlTypeCode(Types.VARCHAR);
//        col.setSqlType("VARCHAR");
//        t.addColumn(col);
//        mappings.addColumnBinding(col.getName(), col, t);
//
//
//        RootClass clazz = new RootClass();
//        clazz.setEntityName("table1");
//        clazz.setLazy(false);
//        clazz.setTable(t);
//
//        Property prop = new Property();
//        prop.setName("col1");
//
//        SimpleValue value = new SimpleValue(mappings, t);
//        value.setTypeName("string");
//        value.addColumn(col);
//
//        prop.setValue(value);
//        clazz.addProperty(prop);
//
//        clazz.createPrimaryKey();
//
//
//        mappings.addClass(clazz);
//        mappings.addImport(clazz.getEntityName(),clazz.getEntityName());
//
        //TODO add mapping to existed classes, if presented
        // see http://www.manydesigns.com/en/blog/configuring-hibernate-programmatically logical model

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);

    }
}
