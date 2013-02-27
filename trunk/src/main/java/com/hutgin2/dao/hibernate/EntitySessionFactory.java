package com.hutgin2.dao.hibernate;

import com.hutgin2.export.hbm.Exporter;
import com.hutgin2.meta.DatabaseModel;
import com.hutgin2.meta.TableMeta;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.*;
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
     * Spring LocalSessionFactoryBean approach. Based on hibernate 3 approach, but wrapped with spring functionality.
     */
    public synchronized void init(DatabaseModel model) {

        // export schema into xml using exporter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new Exporter().export(model, outputStream);


        try {
            FileOutputStream fos = new FileOutputStream("e:/temp.xml");
            fos.write(outputStream.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSourceMain);
        builder.getProperties().putAll(hibernatePropertiesMain);

        Mappings mappings = builder.createMappings();
        for (TableMeta tableMeta : model.getTables()) {
            RootClass rootclass = new RootClass();

            rootclass.setEntityName(tableMeta.getName());
            mappings.addImport(rootclass.getEntityName(), rootclass.getEntityName());


            String logicalTableName;
            String physicalTableName;
//            if ( tableMeta.getName() == null ) {
//                logicalTableName = StringHelper.unqualify(model.getEntityName());
//                physicalTableName = mappings.getNamingStrategy().classToTableName( model.getEntityName() );
//            }
//            else {
            logicalTableName = tableMeta.getName();
            physicalTableName = mappings.getNamingStrategy().tableName(logicalTableName);
//            }
            mappings.addTableBinding(null, null, logicalTableName, physicalTableName, null);
            String classTableName = physicalTableName;
            Table table = mappings.addTable(
                    null,
                    null,
                    classTableName,
                    null,
                    false
            );
            rootclass.setTable(table);


            // ID
            SimpleValue id = new SimpleValue(mappings, rootclass.getTable());
            rootclass.setIdentifier(id);
            String typeName = "java.lang.Long";
            TypeDef typeDef = mappings.getTypeDef(typeName);
            typeName = typeDef.getTypeClass();
            Properties allParameters = new Properties();
            allParameters.putAll(typeDef.getParameters());

            Column column = new Column();
            column.setValue(id);
            final String columnName = "id";
            String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(columnName, "id");
            column.setName(mappings.getNamingStrategy().columnName(columnName));
            table.addColumn(column); // table=null -> an association
            mappings.addColumnBinding(logicalColumnName, column, table);
            id.addColumn(column);


            mappings.addClass(rootclass);
        }

//        builder.addInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        builder.addResource("testData/hbm/sample.hbm.xml"); //TODO for test only!!


        sessionFactory = builder.buildSessionFactory();
    }


    /**
     * Hibernate 4 approach: using metadata source and JAXB parser.
     * currently it ignore default_entity_mode = dynamic_map, or I just didn't find the solution
     */
    public synchronized void initAsMetamodel(DatabaseModel model) {
        // export schema into xml using exporter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new Exporter().export(model, outputStream);

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
}
