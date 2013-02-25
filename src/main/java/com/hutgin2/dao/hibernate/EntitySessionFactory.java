package com.hutgin2.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class EntitySessionFactory {

    @Autowired
    private Properties hibernateProperties;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory() {
        //TODO make as singleton
        LocalSessionFactoryBuilder configuration = new LocalSessionFactoryBuilder(dataSource);
        configuration.setProperties(hibernateProperties);
        Mappings mappings = configuration.createMappings();
        Table t = mappings.addTable("", "", "table1", "", false);
        t.addColumn(new Column("col1"));

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);

    }
}
