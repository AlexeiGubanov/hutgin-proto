package com.hutgin2.dao.hibernate.binderMapping;

import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
import com.hutgin2.meta.TypeHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.MappingException;
import org.hibernate.cfg.Mappings;
import org.hibernate.engine.internal.Versioning;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Table;

public class TableMetaBinder implements Binder {
    private TableMeta tableMeta;

    public TableMetaBinder(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    @Override
    public void bind(Mappings mappings) {
        // TODO check against tableMeta type: root, subclass, join
        // currently all are root classes

        RootClass persistentClass = new RootClass();

        // LAZY
        Boolean lazyNode = TypeHelper.asBoolean(tableMeta.getProperty(TableMeta.PROPERTY_LAZY));
        boolean lazy = lazyNode == null ? mappings.isDefaultLazy() : lazyNode;
        persistentClass.setLazy(lazy);

        // ENTITY NAME
        String entityName = tableMeta.getName();
        persistentClass.setEntityName(entityName);
        persistentClass.setJpaEntityName(StringHelper.unqualify(entityName));


        // POJO
        //TODO check if class has POJO implementor
//        String className = getClassName(node.attribute("name"), mappings);
//        String proxyName = getClassName(node.attribute("proxy"), mappings);
//
//        entity.setClassName(className);
//
//        if (proxyName != null) {
//            entity.setProxyInterfaceName(proxyName);
//            entity.setLazy(true);
//        } else if (entity.isLazy()) {
//            entity.setProxyInterfaceName(className);
//        }
//
//        Element tuplizer = locateTuplizerDefinition(node, EntityMode.POJO);
//        if (tuplizer != null) {
//            entity.addTuplizer(EntityMode.POJO, tuplizer.attributeValue("class"));
//        }

        //DOM4J check designation

        //MAP
        //TODO check tupplizer support
//        Element tuplizer = locateTuplizerDefinition(node, EntityMode.MAP);
//        if (tuplizer != null) {
//            entity.addTuplizer(EntityMode.MAP, tuplizer.attributeValue("class"));
//        }

        //TODO check designation
        // Iterator itr = node.elementIterator("fetch-profile");
//        while (itr.hasNext()) {
//            final Element profileElement = (Element) itr.next();
//            parseFetchProfile(profileElement, mappings, entityName);
//        }

        // DISCRIMINATOR
        String discriminatorNode = null; //TODO check support
        persistentClass.setDiscriminatorValue((discriminatorNode == null)
                ? persistentClass.getEntityName()
                : discriminatorNode);

        // DYNAMIC UPDATE
        Boolean dynamicNode = null; // TODO check support
        persistentClass.setDynamicUpdate(dynamicNode != null && dynamicNode);

        // DYNAMIC INSERT
        Boolean insertNode = null; //TODO check support
        persistentClass.setDynamicInsert(insertNode != null && insertNode);

        // IMPORT
        mappings.addImport(persistentClass.getEntityName(), persistentClass.getEntityName());
        if (mappings.isAutoImport() && persistentClass.getEntityName().indexOf('.') > 0) {
            mappings.addImport(
                    persistentClass.getEntityName(),
                    StringHelper.unqualify(persistentClass.getEntityName())
            );
        }

        // BATCH SIZE
        Integer batchNode = null; //TODO check designation
        if (batchNode != null) persistentClass.setBatchSize(batchNode);

        // SELECT BEFORE UPDATE
        Boolean sbuNode = null; //TODO check desingation
        if (sbuNode != null) persistentClass.setSelectBeforeUpdate(sbuNode);

        // OPTIMISTIC LOCK MODE
        String olNode = null; // TODO check support
        persistentClass.setOptimisticLockMode(getOptimisticLockMode(olNode));

        // PERSISTER
        String persisterNode = null; //TODO check designation
        if (persisterNode != null) {
            try {
                persistentClass.setEntityPersisterClass(ReflectHelper.classForName(
                        persisterNode
                ));
            } catch (ClassNotFoundException cnfe) {
                throw new MappingException("Could not find persister class: "
                        + persisterNode);
            }
        }

        // CUSTOM SQL
//        handleCustomSQL(tableMeta, entity); // TODO check support

//        Iterator tables = node.elementIterator("synchronize"); // TODO check designation
//        while (tables.hasNext()) {
//            entity.addSynchronizedTable(((Element) tables.next()).attributeValue("table"));
//        }

        persistentClass.setAbstract(tableMeta.isVirtual());


        // DB-OBJECTNAME
        String schemaNode = null; //TODO check supoort
        String schema = schemaNode == null ?
                mappings.getSchemaName() : schemaNode;

        String catalogNode = null; //TODO check support
        String catalog = catalogNode == null ?
                mappings.getCatalogName() : catalogNode;

        Table table = mappings.addTable(
                schema,
                catalog,
                getTableNameAndAddToMapping(schema, catalog, mappings),
                getSubselect(),
                persistentClass.isAbstract() != null && persistentClass.isAbstract()
        );
        persistentClass.setTable(table);
        table.setComment(tableMeta.getDescription());

        // MUTABLE
        persistentClass.setMutable(!tableMeta.isVirtual()); // TODO check possibility to support this flag with others properties

        // WHERE //TODO check designation
//        Attribute whereNode = node.attribute("where");
//        if (whereNode != null) entity.setWhere(whereNode.getValue());

        // CHECK     //TODO check designation
//        Attribute chNode = node.attribute("check");
//        if (chNode != null) table.addCheckConstraint(chNode.getValue());

        // POLYMORPHISM
        String polyNode = null;//TODO check designation node.attribute("polymorphism");
        persistentClass.setExplicitPolymorphism((polyNode != null) && polyNode.equals("explicit"));

        // ROW ID  //TODO check designation
//        Attribute rowidNode = node.attribute("rowid");
//        if (rowidNode != null) table.setRowId(rowidNode.getValue());


        for (FieldMeta fieldMeta : tableMeta.getFields()) {
            new FieldMetaBinder(fieldMeta, table, persistentClass).bind(mappings);

        }
        if (persistentClass.getIdentifier() == null) {
            throw new IllegalStateException("The root entity needs to specify an identifier");

        }
        // Primary key constraint
        persistentClass.createPrimaryKey();
        mappings.addClass(persistentClass);
    }

    private String getTableNameAndAddToMapping(
            String schema,
            String catalog,
            Mappings mappings) {
        String logicalTableName = StringUtils.isNotEmpty(tableMeta.getTableName()) ? tableMeta.getTableName() : tableMeta.getName();
        String physicalTableName = mappings.getNamingStrategy().tableName(logicalTableName);
        mappings.addTableBinding(schema, catalog, logicalTableName, physicalTableName, null);
        return physicalTableName;
    }

    private String getSubselect() {
        //TODO check designation and support element.attributeValue("subselect");
        return null;
    }

    public static int getOptimisticLockMode(String olMode) {

        if (olMode == null) return Versioning.OPTIMISTIC_LOCK_VERSION;
        if (olMode == null || "version".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_VERSION;
        } else if ("dirty".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_DIRTY;
        } else if ("all".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_ALL;
        } else if ("none".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_NONE;
        } else {
            throw new MappingException("Unsupported optimistic-lock style: " + olMode);
        }
    }


}
