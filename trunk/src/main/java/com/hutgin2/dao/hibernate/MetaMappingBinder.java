package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.*;
import org.hibernate.EntityMode;
import org.hibernate.FetchMode;
import org.hibernate.cfg.Mappings;
import org.hibernate.cfg.SecondPass;
import org.hibernate.engine.internal.Versioning;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.mapping.*;
import org.hibernate.type.BasicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Bind configuration-time metamodel ({@link org.hibernate.cfg.Mappings}) from meta DataBaseModel
 */
public final class MetaMappingBinder {

    private static final Logger LOG = LoggerFactory.getLogger(MetaMappingBinder.class);

    /**
     * Private constructor to disallow instantiation.
     */
    private MetaMappingBinder() {
    }

    /**
     *
     */
    public static void bindDatabaseModel(
            DatabaseModel model,
            Mappings mappings,
            Map inheritedMetas) {

        //SS Extract package name
//         java.util.List<String> names = HbmBinder.getExtendsNeeded(metadataXml, mappings);
//        if (!names.isEmpty()) {
//            // classes mentioned in extends not available - so put it in queue
//            Attribute packageAttribute = hibernateMappingElement.attribute("package");
//            String packageName = packageAttribute == null ? null : packageAttribute.getValue();
//            for (String name : names) {
//                mappings.addToExtendsQueue(new ExtendsQueueEntry(name, packageName, metadataXml, entityNames));
//            }
//            return;
//        }

        // get meta's from <hibernate-mapping>
        inheritedMetas = getMetas(model, inheritedMetas, true);
        extractRootAttributes(model, mappings);


        // PROCESS TABLES

//            if ("filter-def".equals(elementName)) {
//                parseFilterDef(element, mappings);
//            } else if ("fetch-profile".equals(elementName)) {
//                parseFetchProfile(element, mappings, null);
//            } else if ("identifier-generator".equals(elementName)) {
//                parseIdentifierGeneratorRegistration(element, mappings);
//            } else if ("typedef".equals(elementName)) {
//                bindTypeDef(element, mappings);
//            } else if ("class".equals(elementName)) {
        for (TableMeta tableMeta : model.getTables()) {
            RootClass rootclass = new RootClass();
            bindRootClass(tableMeta, rootclass, mappings, inheritedMetas);
            mappings.addClass(rootclass);
        }
//            } else if ("subclass".equals(elementName)) {
//                PersistentClass superModel = getSuperclass(mappings, element);
//                handleSubclass(superModel, mappings, element, inheritedMetas);
//            } else if ("joined-subclass".equals(elementName)) {
//                PersistentClass superModel = getSuperclass(mappings, element);
//                handleJoinedSubclass(superModel, mappings, element, inheritedMetas);
//            } else if ("union-subclass".equals(elementName)) {
//                PersistentClass superModel = getSuperclass(mappings, element);
//                handleUnionSubclass(superModel, mappings, element, inheritedMetas);
//            } else if ("query".equals(elementName)) {
//                bindNamedQuery(element, null, mappings);
//            } else if ("sql-query".equals(elementName)) {
//                bindNamedSQLQuery(element, null, mappings);
//            } else if ("resultset".equals(elementName)) {
//                bindResultSetMappingDefinition(element, null, mappings);
//            } else if ("import".equals(elementName)) {
//                bindImport(element, mappings);
//            } else if ("database-object".equals(elementName)) {
//                bindAuxiliaryDatabaseObject(element, mappings);
//            }
    }

    //
//    private static void parseIdentifierGeneratorRegistration(Element element, Mappings mappings) {
//        String strategy = element.attributeValue("name");
//        if (StringHelper.isEmpty(strategy)) {
//            throw new MappingException("'name' attribute expected for identifier-generator elements");
//        }
//        String generatorClassName = element.attributeValue("class");
//        if (StringHelper.isEmpty(generatorClassName)) {
//            throw new MappingException("'class' attribute expected for identifier-generator [identifier-generator@name=" + strategy + "]");
//        }
//
//        try {
//            Class generatorClass = ReflectHelper.classForName(generatorClassName);
//            mappings.getIdentifierGeneratorFactory().register(strategy, generatorClass);
//        } catch (ClassNotFoundException e) {
//            throw new MappingException("Unable to locate identifier-generator class [name=" + strategy + ", class=" + generatorClassName + "]");
//        }
//
//    }
//
//    private static void bindImport(Element importNode, Mappings mappings) {
//        String className = getClassName(importNode.attribute("class"), mappings);
//        Attribute renameNode = importNode.attribute("rename");
//        String rename = (renameNode == null) ?
//                StringHelper.unqualify(className) :
//                renameNode.getValue();
//        LOG.debugf("Import: %s -> %s", rename, className);
//        mappings.addImport(className, rename);
//    }
//
//    private static void bindTypeDef(Element typedefNode, Mappings mappings) {
//        String typeClass = typedefNode.attributeValue("class");
//        String typeName = typedefNode.attributeValue("name");
//        Iterator paramIter = typedefNode.elementIterator("param");
//        Properties parameters = new Properties();
//        while (paramIter.hasNext()) {
//            Element param = (Element) paramIter.next();
//            parameters.setProperty(param.attributeValue("name"), param.getTextTrim());
//        }
//        mappings.addTypeDef(typeName, typeClass, parameters);
//    }
//
//    private static void bindAuxiliaryDatabaseObject(Element auxDbObjectNode, Mappings mappings) {
//        AuxiliaryDatabaseObject auxDbObject = null;
//        Element definitionNode = auxDbObjectNode.element("definition");
//        if (definitionNode != null) {
//            try {
//                auxDbObject = (AuxiliaryDatabaseObject) ReflectHelper
//                        .classForName(definitionNode.attributeValue("class"))
//                        .newInstance();
//            } catch (ClassNotFoundException e) {
//                throw new MappingException(
//                        "could not locate custom database object class [" +
//                                definitionNode.attributeValue("class") + "]"
//                );
//            } catch (Throwable t) {
//                throw new MappingException(
//                        "could not instantiate custom database object class [" +
//                                definitionNode.attributeValue("class") + "]"
//                );
//            }
//        } else {
//            auxDbObject = new SimpleAuxiliaryDatabaseObject(
//                    auxDbObjectNode.elementTextTrim("create"),
//                    auxDbObjectNode.elementTextTrim("drop")
//            );
//        }
//
//        Iterator dialectScopings = auxDbObjectNode.elementIterator("dialect-scope");
//        while (dialectScopings.hasNext()) {
//            Element dialectScoping = (Element) dialectScopings.next();
//            auxDbObject.addDialectScope(dialectScoping.attributeValue("name"));
//        }
//
//        mappings.addAuxiliaryDatabaseObject(auxDbObject);
//    }
//
    private static void extractRootAttributes(DatabaseModel model, Mappings mappings) {
//        Attribute schemaNode = hmNode.attribute("schema");
//        mappings.setSchemaName((schemaNode == null) ? null : schemaNode.getValue());
//
//        Attribute catalogNode = hmNode.attribute("catalog");
//        mappings.setCatalogName((catalogNode == null) ? null : catalogNode.getValue());
//
//        Attribute dcNode = hmNode.attribute("default-cascade");
//        mappings.setDefaultCascade((dcNode == null) ? "none" : dcNode.getValue());
//
//        Attribute daNode = hmNode.attribute("default-access");
//        mappings.setDefaultAccess((daNode == null) ? "property" : daNode.getValue());
//
//        Attribute dlNode = hmNode.attribute("default-lazy");
//        mappings.setDefaultLazy(dlNode == null || dlNode.getValue().equals("true"));
//
//        Attribute aiNode = hmNode.attribute("auto-import");
//        mappings.setAutoImport((aiNode == null) || "true".equals(aiNode.getValue()));
//
//        Attribute packNode = hmNode.attribute("package");
//        if (packNode != null) mappings.setDefaultPackage(packNode.getValue());
    }

    public static void bindRootClass(TableMeta tableMeta, RootClass rootClass, Mappings mappings,
                                     java.util.Map inheritedMetas) {
        bindClass(tableMeta, rootClass, mappings, inheritedMetas);
        inheritedMetas = getMetas(tableMeta, inheritedMetas, true); // get meta's from <class>
        bindRootPersistentClassCommonValues(tableMeta, inheritedMetas, mappings, rootClass);
    }


    private static void bindRootPersistentClassCommonValues(TableMeta tableMeta, java.util.Map inheritedMetas, Mappings mappings, RootClass entity) {

        // DB-OBJECTNAME
//FUTURE        Attribute schemaNode = node.attribute("schema");
        String schemaNode = null;
        String schema = schemaNode == null ? mappings.getSchemaName() : schemaNode;

//FUTURE        Attribute catalogNode = node.attribute("catalog");
        String catalogNode = null;
        String catalog = catalogNode == null ? mappings.getCatalogName() : catalogNode;

        Table table = mappings.addTable(
                schema,
                catalog,
                getClassTableName(entity, tableMeta, schema, catalog, null, mappings),
                getSubselect(tableMeta),
                entity.isAbstract() != null && entity.isAbstract()
        );
        entity.setTable(table);
        bindComment(table, tableMeta);

//SS        if (LOG.isDebugEnabled()) {
//            LOG.debugf("Mapping class: %s -> %s", entity.getEntityName(), entity.getTable().getName());
//        }

        // MUTABLE
//FUTURE        Attribute mutableNode = node.attribute("mutable");
        Boolean mutableNode = null;
        entity.setMutable((mutableNode == null) || mutableNode);

        // WHERE
//FUTURE        Attribute whereNode = node.attribute("where");
//        if (whereNode != null) entity.setWhere(whereNode.getValue());

        // CHECK
//FUTURE        Attribute chNode = node.attribute("check");
//        if (chNode != null) table.addCheckConstraint(chNode.getValue());

        // POLYMORPHISM
//FUTURE        Attribute polyNode = node.attribute("polymorphism");
        String polyNode = null;
        entity.setExplicitPolymorphism((polyNode != null) && polyNode.equals("explicit"));

        // ROW ID
//FUTURE        Attribute rowidNode = node.attribute("rowid");
//        if (rowidNode != null) table.setRowId(rowidNode.getValue());

        // PROCESS INDICES AND OTHERS
        for (ConstraintMeta constraintMeta : tableMeta.getConstraints()) {
            switch (constraintMeta.getType()) {
                case PK:
                    // ID
                    if (constraintMeta.getFields().size() == 1) {
                        bindSimpleId(constraintMeta.getFields().get(0), entity, mappings, inheritedMetas);
                    } else {
                        // COMPOSITE-ID
//SS                        bindCompositeId(constraintMeta.getFields(), entity, mappings, inheritedMetas);
                    }
                    break;
            }

        }

//FUTURE        while (subnodes.hasNext()) {
//             if ("version".equals(name) || "timestamp".equals(name)) {
//                // VERSION / TIMESTAMP
//                bindVersioningProperty(table, subnode, mappings, name, entity, inheritedMetas);
//            } else if ("discriminator".equals(name)) {
//                // DISCRIMINATOR
//                bindDiscriminatorProperty(table, entity, subnode, mappings);
//            } else if ("cache".equals(name)) {
//                entity.setCacheConcurrencyStrategy(subnode.attributeValue("usage"));
//                entity.setCacheRegionName(subnode.attributeValue("region"));
//                entity.setLazyPropertiesCacheable(!"non-lazy".equals(subnode.attributeValue("include")));
//            }
//
//        }

        // Primary key constraint
        entity.createPrimaryKey();

        createClassProperties(tableMeta, entity, mappings, inheritedMetas);
    }


    private static void bindSimpleId(FieldMeta fieldMeta, RootClass entity, Mappings mappings,
                                     java.util.Map inheritedMetas) {
        String propertyName = fieldMeta.getName();

        SimpleValue id = new SimpleValue(mappings, entity.getTable());
        entity.setIdentifier(id);

        // NM
        // if ( propertyName == null || entity.getPojoRepresentation() == null ) {
        // bindSimpleValue( idNode, id, false, RootClass.DEFAULT_IDENTIFIER_COLUMN_NAME, mappings );
        // if ( !id.isTypeSpecified() ) {
        // throw new MappingException( "must specify an identifier type: " + entity.getEntityName()
        // );
        // }
        // }
        // else {
        // bindSimpleValue( idNode, id, false, propertyName, mappings );
        // PojoRepresentation pojo = entity.getPojoRepresentation();
        // id.setTypeUsingReflection( pojo.getClassName(), propertyName );
        //
        // Property prop = new Property();
        // prop.setValue( id );
        // bindProperty( idNode, prop, mappings, inheritedMetas );
        // entity.setIdentifierProperty( prop );
        // }

        if (propertyName == null) {
            bindSimpleValue(fieldMeta, id, false, RootClass.DEFAULT_IDENTIFIER_COLUMN_NAME, mappings);
        } else {
            bindSimpleValue(fieldMeta, id, false, propertyName, mappings);
        }
        fieldMeta.setProcessed(true);

//SS        if (propertyName == null || !entity.hasPojoRepresentation()) {
//            if (!id.isTypeSpecified()) {
//                throw new MappingException("must specify an identifier type: "
//                        + entity.getEntityName());
//            }
//        } else {
//            id.setTypeUsingReflection(entity.getClassName(), propertyName);
//        }

        if (propertyName != null) {
            Property prop = new Property();
            prop.setValue(id);
            bindProperty(fieldMeta, prop, mappings, inheritedMetas);
            entity.setIdentifierProperty(prop);
            entity.setDeclaredIdentifierProperty(prop);
        }

        makeIdentifier(fieldMeta, id, mappings);
    }

//SS    private static void bindCompositeId(FieldMeta fieldMeta, RootClass entity, Mappings mappings,
//                                        java.util.Map inheritedMetas) {
//        String propertyName = fieldMeta.getName();
//        Component id = new Component(mappings, entity);
//        entity.setIdentifier(id);
//        bindCompositeId(fieldMeta, id, entity, propertyName, mappings, inheritedMetas);
//        if (propertyName == null) {
//            entity.setEmbeddedIdentifier(id.isEmbedded());
//            if (id.isEmbedded()) {
//                // HHTODO : what is the implication of this?
//                id.setDynamic(!entity.hasPojoRepresentation());
//                /*
//				 * Property prop = new Property(); prop.setName("id");
//				 * prop.setPropertyAccessorName("embedded"); prop.setValue(id);
//				 * entity.setIdentifierProperty(prop);
//				 */
//            }
//        } else {
//            Property prop = new Property();
//            prop.setValue(id);
//            bindProperty(fieldMeta, prop, mappings, inheritedMetas);
//            entity.setIdentifierProperty(prop);
//            entity.setDeclaredIdentifierProperty(prop);
//        }
//
//        makeIdentifier(fieldMeta, id, mappings);
//
//    }

    //    private static void bindVersioningProperty(Table table, Element subnode, Mappings mappings,
//                                               String name, RootClass entity, java.util.Map inheritedMetas) {
//
//        String propertyName = subnode.attributeValue("name");
//        SimpleValue val = new SimpleValue(mappings, table);
//        bindSimpleValue(subnode, val, false, propertyName, mappings);
//        if (!val.isTypeSpecified()) {
//            // this is either a <version/> tag with no type attribute,
//            // or a <timestamp/> tag
//            if ("version".equals(name)) {
//                val.setTypeName("integer");
//            } else {
//                if ("db".equals(subnode.attributeValue("source"))) {
//                    val.setTypeName("dbtimestamp");
//                } else {
//                    val.setTypeName("timestamp");
//                }
//            }
//        }
//        Property prop = new Property();
//        prop.setValue(val);
//        bindProperty(subnode, prop, mappings, inheritedMetas);
//        // for version properties marked as being generated, make sure they are "always"
//        // generated; aka, "insert" is invalid; this is dis-allowed by the DTD,
//        // but just to make sure...
//        if (prop.getGeneration() == PropertyGeneration.INSERT) {
//            throw new MappingException("'generated' attribute cannot be 'insert' for versioning property");
//        }
//        makeVersion(subnode, val);
//        entity.setVersion(prop);
//        entity.addProperty(prop);
//    }
//
//    private static void bindDiscriminatorProperty(Table table, RootClass entity, Element subnode,
//                                                  Mappings mappings) {
//        SimpleValue discrim = new SimpleValue(mappings, table);
//        entity.setDiscriminator(discrim);
//        bindSimpleValue(
//                subnode,
//                discrim,
//                false,
//                RootClass.DEFAULT_DISCRIMINATOR_COLUMN_NAME,
//                mappings
//        );
//        if (!discrim.isTypeSpecified()) {
//            discrim.setTypeName("string");
//            // ( (Column) discrim.getColumnIterator().next() ).setType(type);
//        }
//        entity.setPolymorphic(true);
//        final String explicitForceValue = subnode.attributeValue("force");
//        boolean forceDiscriminatorInSelects = explicitForceValue == null
//                ? mappings.forceDiscriminatorInSelectsByDefault()
//                : "true".equals(explicitForceValue);
//        entity.setForceDiscriminator(forceDiscriminatorInSelects);
//        if ("false".equals(subnode.attributeValue("insert"))) {
//            entity.setDiscriminatorInsertable(false);
//        }
//    }
//
    public static void bindClass(TableMeta tableMeta, PersistentClass persistentClass, Mappings mappings,
                                 java.util.Map inheritedMetas) {
        // FUTURE boolean lazy = (tableMeta.getLazy() != null)? tableMeta.getLazy(): mappings.isDefaultLazy();
        boolean lazy = mappings.isDefaultLazy();
        persistentClass.setLazy(lazy);

        String entityName = tableMeta.getName();
//SS        if (entityName == null) entityName = getClassName(node.attribute("name"), mappings);
//SS        if (entityName == null) {
//            throw new MappingException("Unable to determine entity name");
//        }
        persistentClass.setEntityName(entityName);
        persistentClass.setJpaEntityName(StringHelper.unqualify(entityName));


        bindPojoRepresentation(tableMeta, persistentClass, mappings, inheritedMetas);
        bindDom4jRepresentation(tableMeta, persistentClass, mappings, inheritedMetas);
        bindMapRepresentation(tableMeta, persistentClass, mappings, inheritedMetas);

//SS        Iterator itr = node.elementIterator("fetch-profile");
//        while (itr.hasNext()) {
//            final Element profileElement = (Element) itr.next();
//            parseFetchProfile(profileElement, mappings, entityName);
//        }

        bindPersistentClassCommonValues(tableMeta, persistentClass, mappings, inheritedMetas);
    }

    private static void bindPojoRepresentation(TableMeta tableMeta, PersistentClass entity,
                                               Mappings mappings, java.util.Map metaTags) {

        //FUTURE
        //String className = getClassName(tableMeta.getClassName(), mappings);
        String className = null;
//SS        String proxyName = getClassName(node.attribute("proxy"), mappings);
        String proxyName = null;

        entity.setClassName(className);

        if (proxyName != null) {
            entity.setProxyInterfaceName(proxyName);
            entity.setLazy(true);
        } else if (entity.isLazy()) {
            entity.setProxyInterfaceName(className);
        }

        String tuplizerImplClassName = locateTuplizerDefinition(tableMeta, EntityMode.POJO);
        if (tuplizerImplClassName != null) {
            entity.addTuplizer(EntityMode.POJO, tuplizerImplClassName);
        }
    }

    private static void bindDom4jRepresentation(TableMeta tableMeta, PersistentClass entity,
                                                Mappings mappings, java.util.Map inheritedMetas) {
//SS        String nodeName = node.attributeValue("node");
//        if (nodeName == null) nodeName = StringHelper.unqualify(entity.getEntityName());
//        entity.setNodeName(nodeName);
    }

    private static void bindMapRepresentation(TableMeta tableMeta, PersistentClass entity,
                                              Mappings mappings, java.util.Map inheritedMetas) {
        String tuplizerImplClassName = locateTuplizerDefinition(tableMeta, EntityMode.MAP);
        if (tuplizerImplClassName != null) {
            entity.addTuplizer(EntityMode.MAP, tuplizerImplClassName);
        }
    }


    private static String locateTuplizerDefinition(TableMeta tableMeta, EntityMode entityMode) {
//SS        Iterator itr = container.elements("tuplizer").iterator();
//        while (itr.hasNext()) {
//            final Element tuplizerElem = (Element) itr.next();
//            if (entityMode.toString().equals(tuplizerElem.attributeValue("entity-mode"))) {
//                return tuplizerElem;
//            }
//        }
        return null;
    }

    private static void bindPersistentClassCommonValues(TableMeta tableMeta, PersistentClass entity, Mappings mappings, java.util.Map inheritedMetas) {
        // DISCRIMINATOR
//FUTURE        Attribute discriminatorNode = node.attribute("discriminator-value");
        String discriminatorNode = null;
        entity.setDiscriminatorValue((discriminatorNode == null) ? entity.getEntityName() : discriminatorNode);

        // DYNAMIC UPDATE
//FUTURE         Attribute dynamicNode = node.attribute("dynamic-update");
        Boolean dynamicNode = null;
        entity.setDynamicUpdate(dynamicNode != null && dynamicNode);

        // DYNAMIC INSERT
//FUTURE         Attribute insertNode = node.attribute("dynamic-insert");
        Boolean insertNode = null;
        entity.setDynamicInsert(insertNode != null && insertNode);

        // IMPORT
        mappings.addImport(entity.getEntityName(), entity.getEntityName());
        if (mappings.isAutoImport() && entity.getEntityName().indexOf('.') > 0) {
            mappings.addImport(
                    entity.getEntityName(),
                    StringHelper.unqualify(entity.getEntityName())
            );
        }

        // BATCH SIZE
//FUTURE         Attribute batchNode = node.attribute("batch-size");
//        if (batchNode != null) entity.setBatchSize(Integer.parseInt(batchNode.getValue()));

        // SELECT BEFORE UPDATE
//FUTURE         Attribute sbuNode = node.attribute("select-before-update");
//        if (sbuNode != null) entity.setSelectBeforeUpdate("true".equals(sbuNode.getValue()));

        // OPTIMISTIC LOCK MODE
//FUTURE         Attribute olNode = node.attribute("optimistic-lock");
        String olNode = null;
        entity.setOptimisticLockMode(getOptimisticLockMode(olNode));

        entity.setMetaAttributes(getMetas(tableMeta, inheritedMetas));

        // PERSISTER
//FUTURE         Attribute persisterNode = node.attribute("persister");
//        if (persisterNode != null) {
//            try {
//                entity.setEntityPersisterClass(ReflectHelper.classForName(
//                        persisterNode
//                                .getValue()
//                ));
//            } catch (ClassNotFoundException cnfe) {
//                throw new MappingException("Could not find persister class: "
//                        + persisterNode.getValue());
//            }
//        }

        // CUSTOM SQL
        handleCustomSQL(tableMeta, entity);

//FUTURE         Iterator tables = node.elementIterator("synchronize");
//        while (tables.hasNext()) {
//            entity.addSynchronizedTable(((Element) tables.next()).attributeValue("table"));
//        }

        Boolean isAbstract = !tableMeta.isPersistable();
        entity.setAbstract(isAbstract);
    }

    private static void handleCustomSQL(TableMeta tableMeta, PersistentClass model) {
//FUTURE        Element element = node.element("sql-insert");
//        if (element != null) {
//            boolean callable = isCallable(element);
//            model.setCustomSQLInsert(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-delete");
//        if (element != null) {
//            boolean callable = isCallable(element);
//            model.setCustomSQLDelete(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-update");
//        if (element != null) {
//            boolean callable = isCallable(element);
//            model.setCustomSQLUpdate(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("loader");
//        if (element != null) {
//            model.setLoaderName(element.attributeValue("query-ref"));
//        }
    }

//    private static void handleCustomSQL(Element node, Join model) throws MappingException {
//        Element element = node.element("sql-insert");
//        if (element != null) {
//            boolean callable = isCallable(element);
//            model.setCustomSQLInsert(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-delete");
//        if (element != null) {
//            boolean callable = isCallable(element);
//            model.setCustomSQLDelete(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-update");
//        if (element != null) {
//            boolean callable = isCallable(element);
//            model.setCustomSQLUpdate(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//    }
//
//    private static void handleCustomSQL(Element node, Collection model) throws MappingException {
//        Element element = node.element("sql-insert");
//        if (element != null) {
//            boolean callable = isCallable(element, true);
//            model.setCustomSQLInsert(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-delete");
//        if (element != null) {
//            boolean callable = isCallable(element, true);
//            model.setCustomSQLDelete(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-update");
//        if (element != null) {
//            boolean callable = isCallable(element, true);
//            model.setCustomSQLUpdate(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//
//        element = node.element("sql-delete-all");
//        if (element != null) {
//            boolean callable = isCallable(element, true);
//            model.setCustomSQLDeleteAll(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
//        }
//    }
//
//    private static boolean isCallable(Element e) throws MappingException {
//        return isCallable(e, true);
//    }
//
//    private static boolean isCallable(Element element, boolean supportsCallable)
//            throws MappingException {
//        Attribute attrib = element.attribute("callable");
//        if (attrib != null && "true".equals(attrib.getValue())) {
//            if (!supportsCallable) {
//                throw new MappingException("callable attribute not supported yet!");
//            }
//            return true;
//        }
//        return false;
//    }
//
//    private static ExecuteUpdateResultCheckStyle getResultCheckStyle(Element element, boolean callable) throws MappingException {
//        Attribute attr = element.attribute("check");
//        if (attr == null) {
//            // use COUNT as the default.  This mimics the old behavior, although
//            // NONE might be a better option moving forward in the case of callable
//            return ExecuteUpdateResultCheckStyle.COUNT;
//        }
//        return ExecuteUpdateResultCheckStyle.fromExternalName(attr.getValue());
//    }
//
//    public static void bindUnionSubclass(Element node, UnionSubclass unionSubclass,
//                                         Mappings mappings, java.util.Map inheritedMetas) throws MappingException {
//
//        bindClass(node, unionSubclass, mappings, inheritedMetas);
//        inheritedMetas = getMetas(node, inheritedMetas, true); // get meta's from <subclass>
//
//        Attribute schemaNode = node.attribute("schema");
//        String schema = schemaNode == null ?
//                mappings.getSchemaName() : schemaNode.getValue();
//
//        Attribute catalogNode = node.attribute("catalog");
//        String catalog = catalogNode == null ?
//                mappings.getCatalogName() : catalogNode.getValue();
//
//        Table denormalizedSuperTable = unionSubclass.getSuperclass().getTable();
//        Table mytable = mappings.addDenormalizedTable(
//                schema,
//                catalog,
//                getClassTableName(unionSubclass, node, schema, catalog, denormalizedSuperTable, mappings),
//                unionSubclass.isAbstract() != null && unionSubclass.isAbstract(),
//                getSubselect(node),
//                denormalizedSuperTable
//        );
//        unionSubclass.setTable(mytable);
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debugf("Mapping union-subclass: %s -> %s", unionSubclass.getEntityName(), unionSubclass.getTable().getName());
//        }
//
//        createClassProperties(node, unionSubclass, mappings, inheritedMetas);
//
//    }
//
//    public static void bindSubclass(Element node, Subclass subclass, Mappings mappings,
//                                    java.util.Map inheritedMetas) throws MappingException {
//
//        bindClass(node, subclass, mappings, inheritedMetas);
//        inheritedMetas = getMetas(node, inheritedMetas, true); // get meta's from <subclass>
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debugf("Mapping subclass: %s -> %s", subclass.getEntityName(), subclass.getTable().getName());
//        }
//
//        // properties
//        createClassProperties(node, subclass, mappings, inheritedMetas);
//    }

    private static String getClassTableName(
            PersistentClass model,
            TableMeta tableMeta,
            String schema,
            String catalog,
            Table denormalizedSuperTable,
            Mappings mappings) {
        String tableNameNode = tableMeta.getName();
        String logicalTableName;
        String physicalTableName;
        if (tableNameNode == null) {
            logicalTableName = StringHelper.unqualify(model.getEntityName());
            physicalTableName = mappings.getNamingStrategy().classToTableName(model.getEntityName());
        } else {
            logicalTableName = tableNameNode;
            physicalTableName = mappings.getNamingStrategy().tableName(logicalTableName);
        }
        mappings.addTableBinding(schema, catalog, logicalTableName, physicalTableName, denormalizedSuperTable);
        return physicalTableName;
    }

    //    public static void bindJoinedSubclass(Element node, JoinedSubclass joinedSubclass,
//                                          Mappings mappings, java.util.Map inheritedMetas) throws MappingException {
//
//        bindClass(node, joinedSubclass, mappings, inheritedMetas);
//        inheritedMetas = getMetas(node, inheritedMetas, true); // get meta's from
//        // <joined-subclass>
//
//        // joined subclasses
//        Attribute schemaNode = node.attribute("schema");
//        String schema = schemaNode == null ?
//                mappings.getSchemaName() : schemaNode.getValue();
//
//        Attribute catalogNode = node.attribute("catalog");
//        String catalog = catalogNode == null ?
//                mappings.getCatalogName() : catalogNode.getValue();
//
//        Table mytable = mappings.addTable(
//                schema,
//                catalog,
//                getClassTableName(joinedSubclass, node, schema, catalog, null, mappings),
//                getSubselect(node),
//                false
//        );
//        joinedSubclass.setTable(mytable);
//        bindComment(mytable, node);
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debugf("Mapping joined-subclass: %s -> %s", joinedSubclass.getEntityName(), joinedSubclass.getTable().getName());
//        }
//
//        // KEY
//        Element keyNode = node.element("key");
//        SimpleValue key = new DependantValue(mappings, mytable, joinedSubclass.getIdentifier());
//        joinedSubclass.setKey(key);
//        key.setCascadeDeleteEnabled("cascade".equals(keyNode.attributeValue("on-delete")));
//        bindSimpleValue(keyNode, key, false, joinedSubclass.getEntityName(), mappings);
//
//        // model.getKey().setType( new Type( model.getIdentifier() ) );
//        joinedSubclass.createPrimaryKey();
//        joinedSubclass.createForeignKey();
//
//        // CHECK
//        Attribute chNode = node.attribute("check");
//        if (chNode != null) mytable.addCheckConstraint(chNode.getValue());
//
//        // properties
//        createClassProperties(node, joinedSubclass, mappings, inheritedMetas);
//
//    }
//
//    private static void bindJoin(Element node, Join join, Mappings mappings,
//                                 java.util.Map inheritedMetas) throws MappingException {
//
//        PersistentClass persistentClass = join.getPersistentClass();
//        String path = persistentClass.getEntityName();
//
//        // TABLENAME
//
//        Attribute schemaNode = node.attribute("schema");
//        String schema = schemaNode == null ?
//                mappings.getSchemaName() : schemaNode.getValue();
//        Attribute catalogNode = node.attribute("catalog");
//        String catalog = catalogNode == null ?
//                mappings.getCatalogName() : catalogNode.getValue();
//        Table primaryTable = persistentClass.getTable();
//        Table table = mappings.addTable(
//                schema,
//                catalog,
//                getClassTableName(persistentClass, node, schema, catalog, primaryTable, mappings),
//                getSubselect(node),
//                false
//        );
//        join.setTable(table);
//        bindComment(table, node);
//
//        Attribute fetchNode = node.attribute("fetch");
//        if (fetchNode != null) {
//            join.setSequentialSelect("select".equals(fetchNode.getValue()));
//        }
//
//        Attribute invNode = node.attribute("inverse");
//        if (invNode != null) {
//            join.setInverse("true".equals(invNode.getValue()));
//        }
//
//        Attribute nullNode = node.attribute("optional");
//        if (nullNode != null) {
//            join.setOptional("true".equals(nullNode.getValue()));
//        }
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debugf("Mapping class join: %s -> %s", persistentClass.getEntityName(), join.getTable().getName());
//        }
//
//        // KEY
//        Element keyNode = node.element("key");
//        SimpleValue key = new DependantValue(mappings, table, persistentClass.getIdentifier());
//        join.setKey(key);
//        key.setCascadeDeleteEnabled("cascade".equals(keyNode.attributeValue("on-delete")));
//        bindSimpleValue(keyNode, key, false, persistentClass.getEntityName(), mappings);
//
//        // join.getKey().setType( new Type( lazz.getIdentifier() ) );
//        join.createPrimaryKey();
//        join.createForeignKey();
//
//        // PROPERTIES
//        Iterator iter = node.elementIterator();
//        while (iter.hasNext()) {
//            Element subnode = (Element) iter.next();
//            String name = subnode.getName();
//            String propertyName = subnode.attributeValue("name");
//
//            Value value = null;
//            if ("many-to-one".equals(name)) {
//                value = new ManyToOne(mappings, table);
//                bindManyToOne(subnode, (ManyToOne) value, propertyName, true, mappings);
//            } else if ("any".equals(name)) {
//                value = new Any(mappings, table);
//                bindAny(subnode, (Any) value, true, mappings);
//            } else if ("property".equals(name)) {
//                value = new SimpleValue(mappings, table);
//                bindSimpleValue(subnode, (SimpleValue) value, true, propertyName, mappings);
//            } else if ("component".equals(name) || "dynamic-component".equals(name)) {
//                String subpath = StringHelper.qualify(path, propertyName);
//                value = new Component(mappings, join);
//                bindComponent(
//                        subnode,
//                        (Component) value,
//                        join.getPersistentClass().getClassName(),
//                        propertyName,
//                        subpath,
//                        true,
//                        false,
//                        mappings,
//                        inheritedMetas,
//                        false
//                );
//            }
//
//            if (value != null) {
//                Property prop = createProperty(value, propertyName, persistentClass
//                        .getEntityName(), subnode, mappings, inheritedMetas);
//                prop.setOptional(join.isOptional());
//                join.addProperty(prop);
//            }
//
//        }
//
//        // CUSTOM SQL
//        handleCustomSQL(node, join);
//
//    }
//
    public static void bindColumns(final FieldMeta fieldMeta, final SimpleValue simpleValue,
                                   final boolean isNullable, final boolean autoColumn, final String propertyPath,
                                   final Mappings mappings) {

        Table table = simpleValue.getTable();

        Column column = new Column();
        column.setValue(simpleValue);
        bindColumn(fieldMeta, column, isNullable);
        if (column.isUnique() && ManyToOne.class.isInstance(simpleValue)) {
            ((ManyToOne) simpleValue).markAsLogicalOneToOne();
        }
        final String columnName = fieldMeta.getName();
        String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(
                columnName, propertyPath
        );
        column.setName(mappings.getNamingStrategy().columnName(columnName));
        if (table != null) {
            table.addColumn(column); // table=null -> an association - fill
            // it in later
            //HHTODO fill in the mappings for table == null
            mappings.addColumnBinding(logicalColumnName, column, table);
        }
        simpleValue.addColumn(column);
//TODO !        bindIndex(node.attribute("index"), table, column, mappings);
//TODO !        bindUniqueKey(node.attribute("unique-key"), table, column, mappings);

//SS        // COLUMN(S)
//        Attribute columnAttribute = node.attribute("column");
//        if (columnAttribute == null) {
//            Iterator itr = node.elementIterator();
//            int count = 0;
//            while (itr.hasNext()) {
//                Element columnElement = (Element) itr.next();
//                if (columnElement.getName().equals("column")) {
//                    Column column = new Column();
//                    column.setValue(simpleValue);
//                    column.setTypeIndex(count++);
//                    bindColumn(columnElement, column, isNullable);
//                    final String columnName = columnElement.attributeValue("name");
//                    String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(
//                            columnName, propertyPath
//                    );
//                    column.setName(mappings.getNamingStrategy().columnName(
//                            columnName));
//                    if (table != null) {
//                        table.addColumn(column); // table=null -> an association
//                        // - fill it in later
//                        //HHTODO fill in the mappings for table == null
//                        mappings.addColumnBinding(logicalColumnName, column, table);
//                    }
//
//
//                    simpleValue.addColumn(column);
//                    // column index
//                    bindIndex(columnElement.attribute("index"), table, column, mappings);
//                    bindIndex(node.attribute("index"), table, column, mappings);
//                    //column unique-key
//                    bindUniqueKey(columnElement.attribute("unique-key"), table, column, mappings);
//                    bindUniqueKey(node.attribute("unique-key"), table, column, mappings);
//                } else if (columnElement.getName().equals("formula")) {
//                    Formula formula = new Formula();
//                    formula.setFormula(columnElement.getText());
//                    simpleValue.addFormula(formula);
//                }
//            }
//
//            // HHTODO : another GoodThing would be to go back after all parsing and see if all the columns
//            // (and no formulas) are contained in a defined unique key that only contains these columns.
//            // That too would mark this as a logical one-to-one
//            final Attribute uniqueAttribute = node.attribute("unique");
//            if (uniqueAttribute != null
//                    && "true".equals(uniqueAttribute.getValue())
//                    && ManyToOne.class.isInstance(simpleValue)) {
//                ((ManyToOne) simpleValue).markAsLogicalOneToOne();
//            }
//        } else {
//            if (node.elementIterator("column").hasNext()) {
//                throw new MappingException(
//                        "column attribute may not be used together with <column> subelement");
//            }
//            if (node.elementIterator("formula").hasNext()) {
//                throw new MappingException(
//                        "column attribute may not be used together with <formula> subelement");
//            }
//
//            Column column = new Column();
//            column.setValue(simpleValue);
//            bindColumn(node, column, isNullable);
//            if (column.isUnique() && ManyToOne.class.isInstance(simpleValue)) {
//                ((ManyToOne) simpleValue).markAsLogicalOneToOne();
//            }
//            final String columnName = columnAttribute.getValue();
//            String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(
//                    columnName, propertyPath
//            );
//            column.setName(mappings.getNamingStrategy().columnName(columnName));
//            if (table != null) {
//                table.addColumn(column); // table=null -> an association - fill
//                // it in later
//                //HHTODO fill in the mappings for table == null
//                mappings.addColumnBinding(logicalColumnName, column, table);
//            }
//            simpleValue.addColumn(column);
//            bindIndex(node.attribute("index"), table, column, mappings);
//            bindUniqueKey(node.attribute("unique-key"), table, column, mappings);
//        }

//SS        if (autoColumn && simpleValue.getColumnSpan() == 0) {
//            Column column = new Column();
//            column.setValue(simpleValue);
//            bindColumn(node, column, isNullable);
//            column.setName(mappings.getNamingStrategy().propertyToColumnName(propertyPath));
//            String logicalName = mappings.getNamingStrategy().logicalColumnName(null, propertyPath);
//            mappings.addColumnBinding(logicalName, column, table);
//			/* HHTODO: joinKeyColumnName & foreignKeyColumnName should be called either here or at a
//			 * slightly higer level in the stack (to get all the information we need)
//			 * Right now HbmMetadataSourceProcessorImpl does not support the
//			 */
//            simpleValue.getTable().addColumn(column);
//            simpleValue.addColumn(column);
//            bindIndex(node.attribute("index"), table, column, mappings);
//            bindUniqueKey(node.attribute("unique-key"), table, column, mappings);
//        }

    }

//    private static void bindIndex(Attribute indexAttribute, Table table, Column column, Mappings mappings) {
//        if (indexAttribute != null && table != null) {
//            StringTokenizer tokens = new StringTokenizer(indexAttribute.getValue(), ", ");
//            while (tokens.hasMoreTokens()) {
//                table.getOrCreateIndex(tokens.nextToken()).addColumn(column);
//            }
//        }
//    }
//
//    private static void bindUniqueKey(Attribute uniqueKeyAttribute, Table table, Column column, Mappings mappings) {
//        if (uniqueKeyAttribute != null && table != null) {
//            StringTokenizer tokens = new StringTokenizer(uniqueKeyAttribute.getValue(), ", ");
//            while (tokens.hasMoreTokens()) {
//                table.getOrCreateUniqueKey(tokens.nextToken()).addColumn(column);
//            }
//        }
//    }

    // automatically makes a column with the default name if none is specifed by XML
    public static void bindSimpleValue(FieldMeta fieldMeta, SimpleValue simpleValue, boolean isNullable,
                                       String path, Mappings mappings) {
        bindSimpleValueType(fieldMeta, simpleValue, mappings);

        bindColumnsOrFormula(fieldMeta, simpleValue, path, isNullable, mappings);

//FUTURE        Attribute fkNode = node.attribute("foreign-key");
        String fkNode = null;
        if (fkNode != null) simpleValue.setForeignKeyName(fkNode);
    }

    private static void bindSimpleValueType(FieldMeta fieldMeta, SimpleValue simpleValue, Mappings mappings) {
        String typeName = null;

        Properties parameters = new Properties();


//FUTURE RESOLVE TYPE PARAMS            Iterator typeParameters = typeChild.elementIterator("param");
//
//            while (typeParameters.hasNext()) {
//                Element paramElement = (Element) typeParameters.next();
//                parameters.setProperty(
//                        paramElement.attributeValue("name"),
//                        paramElement.getTextTrim()
//                );
//            }

        resolveAndBindTypeDef(simpleValue, mappings, fieldMeta.getType().getName(), parameters);
    }

    private static void resolveAndBindTypeDef(SimpleValue simpleValue,
                                              Mappings mappings, String typeName, Properties parameters) {

        TypeDef typeDef = mappings.getTypeDef(typeName);
        if (typeDef != null) {
            typeName = typeDef.getTypeClass();
            // parameters on the property mapping should
            // override parameters in the typedef
            Properties allParameters = new Properties();
            allParameters.putAll(typeDef.getParameters());
            allParameters.putAll(parameters);
            parameters = allParameters;
        } else if (typeName != null && !mappings.isInSecondPass()) {
            BasicType basicType = mappings.getTypeResolver().basic(typeName);
            if (basicType == null) {
                /*
                 * If the referenced typeName isn't a basic-type, it's probably a typedef defined
				 * in a mapping file not read yet.
				 * It should be solved by deferring the resolution and binding of this type until
				 * all mapping files are read - the second passes.
				 * Fixes issue HHH-7300
				 */
                SecondPass resolveUserTypeMappingSecondPass = new ResolveUserTypeMappingSecondPass(simpleValue, typeName, mappings, parameters);
                mappings.addSecondPass(resolveUserTypeMappingSecondPass);
            }
        }

        if (!parameters.isEmpty()) simpleValue.setTypeParameters(parameters);
        if (typeName != null) simpleValue.setTypeName(typeName);
    }

    public static void bindProperty(
            FieldMeta fieldMeta,
            Property property,
            Mappings mappings,
            java.util.Map inheritedMetas) {

        String propName = fieldMeta.getName();
        property.setName(propName);
//SS        String nodeName = node.attributeValue("node");
        String nodeName = null;
        if (nodeName == null) nodeName = propName;
        property.setNodeName(nodeName);

//SS        Attribute accessNode = node.attribute("access");
//        if (accessNode != null) {
//            property.setPropertyAccessorName(accessNode.getValue());
//        } else if (node.getName().equals("properties")) {
//            property.setPropertyAccessorName("embedded");
//        } else {
//            property.setPropertyAccessorName(mappings.getDefaultAccess());
//        }

//SS        Attribute cascadeNode = node.attribute("cascade");
//        property.setCascade(cascadeNode == null ? mappings.getDefaultCascade() : cascadeNode
//                .getValue());

//FUTURE        Attribute updateNode = node.attribute("update");
//        property.setUpdateable(updateNode == null || "true".equals(updateNode.getValue()));

//FUTURE        Attribute insertNode = node.attribute("insert");
//        property.setInsertable(insertNode == null || "true".equals(insertNode.getValue()));

//FUTURE        Attribute lockNode = node.attribute("optimistic-lock");
//        property.setOptimisticLocked(lockNode == null || "true".equals(lockNode.getValue()));

//FUTURE        Attribute generatedNode = node.attribute("generated");
//        String generationName = generatedNode == null ? null : generatedNode.getValue();
//        PropertyGeneration generation = PropertyGeneration.parse(generationName);
//        property.setGeneration(generation);

//        if (generation == PropertyGeneration.ALWAYS || generation == PropertyGeneration.INSERT) {
//            // generated properties can *never* be insertable...
//            if (property.isInsertable()) {
//                if (insertNode == null) {
//                    // insertable simply because that is the user did not specify
//                    // anything; just override it
//                    property.setInsertable(false);
//                } else {
//                    // the user specifically supplied insert="true",
//                    // which constitutes an illegal combo
//                    throw new MappingException(
//                            "cannot specify both insert=\"true\" and generated=\"" + generation.getName() +
//                                    "\" for property: " +
//                                    propName
//                    );
//                }
//            }

//            // properties generated on update can never be updateable...
//            if (property.isUpdateable() && generation == PropertyGeneration.ALWAYS) {
//                if (updateNode == null) {
//                    // updateable only because the user did not specify
//                    // anything; just override it
//                    property.setUpdateable(false);
//                } else {
//                    // the user specifically supplied update="true",
//                    // which constitutes an illegal combo
//                    throw new MappingException(
//                            "cannot specify both update=\"true\" and generated=\"" + generation.getName() +
//                                    "\" for property: " +
//                                    propName
//                    );
//                }
//            }
//        }

//SS        boolean isLazyable = "property".equals(node.getName()) ||
//                "component".equals(node.getName()) ||
//                "many-to-one".equals(node.getName()) ||
//                "one-to-one".equals(node.getName()) ||
//                "any".equals(node.getName());
//        if (isLazyable) {
//            Attribute lazyNode = node.attribute("lazy");
//            property.setLazy(lazyNode != null && "true".equals(lazyNode.getValue()));
//        }
//
//        if (LOG.isDebugEnabled()) {
//            String msg = "Mapped property: " + property.getName();
//            String columns = columns(property.getValue());
//            if (columns.length() > 0) msg += " -> " + columns;
//            LOG.debug(msg);
//        }

        property.setMetaAttributes(getMetas(fieldMeta, inheritedMetas));

    }

    //    private static String columns(Value val) {
//        StringBuilder columns = new StringBuilder();
//        Iterator iter = val.getColumnIterator();
//        while (iter.hasNext()) {
//            columns.append(((Selectable) iter.next()).getText());
//            if (iter.hasNext()) columns.append(", ");
//        }
//        return columns.toString();
//    }

    /**
     * Called for all collections
     */
    public static void bindCollection(FieldMeta fieldMeta, Collection collection, String className,
                                      String path, Mappings mappings, java.util.Map inheritedMetas) {

        // ROLENAME
        collection.setRole(path);

//FUTURE        Attribute inverseNode = node.attribute("inverse");
        Boolean inverseNode = null;
        if (inverseNode != null) {
            collection.setInverse(inverseNode);
        }


//FUTURE        Attribute mutableNode = node.attribute("mutable");
        Boolean mutableNode = null;
        if (mutableNode != null) {
            collection.setMutable(mutableNode);
        }

//FUTURE        Attribute olNode = node.attribute("optimistic-lock");
        Boolean olNode = null;
        collection.setOptimisticLocked(olNode == null || olNode);

//FUTURE        Attribute orderNode = node.attribute("order-by");
        String orderNode = null;
        if (orderNode != null) {
            collection.setOrderBy(orderNode);
        }
//FUTURE        Attribute whereNode = node.attribute("where");
        String whereNode = null;
        if (whereNode != null) {
            collection.setWhere(whereNode);
        }
//FUTURE        Attribute batchNode = node.attribute("batch-size");
        String batchNode = null;
        if (batchNode != null) {
            collection.setBatchSize(Integer.parseInt(batchNode));
        }

        String nodeName = fieldMeta.getName();
        collection.setNodeName(nodeName);

//SS        String embed = node.attributeValue("embed-xml");
//        // sometimes embed is set to the default value when not specified in the mapping,
//        // so can't seem to determine if an attribute was explicitly set;
//        // log a warning if embed has a value different from the default.
//        if (!StringHelper.isEmpty(embed) && !"true".equals(embed)) {
//            LOG.embedXmlAttributesNoLongerSupported();
//        }
//        collection.setEmbedded(embed == null || "true".equals(embed));


        // PERSISTER
//SS        Attribute persisterNode = node.attribute("persister");
//        if (persisterNode != null) {
//            try {
//                collection.setCollectionPersisterClass(ReflectHelper.classForName(persisterNode
//                        .getValue()));
//            } catch (ClassNotFoundException cnfe) {
//                throw new MappingException("Could not find collection persister class: "
//                        + persisterNode.getValue());
//            }
//        }

//FUTURE        Attribute typeNode = node.attribute("collection-type");
        String typeNode = null;
        if (typeNode != null) {
            String typeName = typeNode;
            TypeDef typeDef = mappings.getTypeDef(typeName);
            if (typeDef != null) {
                collection.setTypeName(typeDef.getTypeClass());
                collection.setTypeParameters(typeDef.getParameters());
            } else {
                collection.setTypeName(typeName);
            }
        }

        // FETCH STRATEGY

        initOuterJoinFetchSetting(fieldMeta, collection);

//FUTURE        if ("subselect".equals(node.attributeValue("fetch"))) {
//            collection.setSubselectLoadable(true);
//            collection.getOwner().setSubselectLoadableCollections(true);
//        }

        initLaziness(fieldMeta, collection, mappings, "true", mappings.isDefaultLazy());

//SS        //HHTODO: suck this into initLaziness!
//        if ("extra".equals(node.attributeValue("lazy"))) {
//            collection.setLazy(true);
//            collection.setExtraLazy(true);
//        }

        if (fieldMeta.getAssociationType() == FieldAssociationType.ONE_TO_MANY) {
            OneToMany oneToMany = new OneToMany(mappings, collection.getOwner());
            collection.setElement(oneToMany);
            bindOneToMany(fieldMeta, oneToMany, mappings);
            // we have to set up the table later!! yuck
        } else {
            // TABLE
            TableMeta tableRef = fieldMeta.getFieldRef().getTable();
            String tableName;
            if (tableRef != null) {
                tableName = mappings.getNamingStrategy().tableName(tableRef.getName());
            } else {
                Table ownerTable = collection.getOwner().getTable();
                //HHTODO mappings.getLogicalTableName(ownerTable)
                String logicalOwnerTableName = ownerTable.getName();
                //HHFIXME we don't have the associated entity table name here, has to be done in a second pass
                tableName = mappings.getNamingStrategy().collectionTableName(
                        collection.getOwner().getEntityName(),
                        logicalOwnerTableName,
                        null,
                        null,
                        path
                );
                if (ownerTable.isQuoted()) {
                    tableName = StringHelper.quote(tableName);
                }
            }
//FUTURE            Attribute schemaNode = node.attribute("schema");
            String schemaNode = null;
            String schema = schemaNode == null ?
                    mappings.getSchemaName() : schemaNode;

//FUTURE            Attribute catalogNode = node.attribute("catalog");
            String catalogNode = null;
            String catalog = catalogNode == null ?
                    mappings.getCatalogName() : catalogNode;

            Table table = mappings.addTable(
                    schema,
                    catalog,
                    tableName,
                    getSubselect(fieldMeta),
                    false
            );
            collection.setCollectionTable(table);
//SS            bindComment(table, fieldMeta);
        }

        // SORT
//FUTURE        Attribute sortedAtt = node.attribute("sort");
//        // unsorted, natural, comparator.class.name
//        if (sortedAtt == null || sortedAtt.getValue().equals("unsorted")) {
//            collection.setSorted(false);
//        } else {
//            collection.setSorted(true);
//            String comparatorClassName = sortedAtt.getValue();
//            if (!comparatorClassName.equals("natural")) {
//                collection.setComparatorClassName(comparatorClassName);
//            }
//        }

        // ORPHAN DELETE (used for programmer error detection)
//FUTURE        Attribute cascadeAtt = node.attribute("cascade");
//        if (cascadeAtt != null && cascadeAtt.getValue().indexOf("delete-orphan") >= 0) {
//            collection.setOrphanDelete(true);
//        }

        // CUSTOM SQL
//FUTURE        handleCustomSQL(node, collection);

        // set up second pass
        if (collection instanceof List) {
            mappings.addSecondPass(new ListSecondPass(fieldMeta, mappings, (org.hibernate.mapping.List) collection, inheritedMetas));
        } else if (collection instanceof Map) {
            mappings.addSecondPass(new MapSecondPass(fieldMeta, mappings, (org.hibernate.mapping.Map) collection, inheritedMetas));
        } else if (collection instanceof IdentifierCollection) {
            mappings.addSecondPass(new IdentifierCollectionSecondPass(
                    fieldMeta,
                    mappings,
                    collection,
                    inheritedMetas
            ));
        } else {
            mappings.addSecondPass(new CollectionSecondPass(fieldMeta, mappings, collection, inheritedMetas));
        }

//SS        Iterator iter = node.elementIterator("filter");
//        while (iter.hasNext()) {
//            final Element filter = (Element) iter.next();
//            parseFilter(filter, collection, mappings);
//        }

//SS        Iterator tables = node.elementIterator("synchronize");
//        while (tables.hasNext()) {
//            collection.getSynchronizedTables().add(
//                    ((Element) tables.next()).attributeValue("table"));
//        }

//SS        Element element = node.element("loader");
//        if (element != null) {
//            collection.setLoaderName(element.attributeValue("query-ref"));
//        }

//TODO        collection.setReferencedPropertyName(node.element("key").attributeValue("property-ref"));
        collection.setReferencedPropertyName(fieldMeta.getFieldRef().getName());
    }

    private static void initLaziness(
            FieldMeta node,
            Fetchable fetchable,
            Mappings mappings,
            String proxyVal,
            boolean defaultLazy
    ) {
//FUTURE        Attribute lazyNode = node.attribute("lazy");
//        boolean isLazyTrue = lazyNode == null ?
//                defaultLazy && fetchable.isLazy() : //fetch="join" overrides default laziness
//                lazyNode.getValue().equals(proxyVal); //fetch="join" overrides default laziness
//        fetchable.setLazy(isLazyTrue);
    }

    private static void initLaziness(
            FieldMeta node,
            ToOne fetchable,
            Mappings mappings,
            boolean defaultLazy
    ) {
//FUTURE        if ("no-proxy".equals(node.attributeValue("lazy"))) {
//            fetchable.setUnwrapProxy(true);
//            fetchable.setLazy(true);
//            //HHTODO: better to degrade to lazy="false" if uninstrumented
//        } else {
//            initLaziness(node, fetchable, mappings, "proxy", defaultLazy);
//        }
    }

    private static void bindColumnsOrFormula(FieldMeta fieldMeta, SimpleValue simpleValue, String path,
                                             boolean isNullable, Mappings mappings) {
//FUTURE        Attribute formulaNode = node.attribute("formula");
        String formulaNode = null;
        if (formulaNode != null) {
            Formula f = new Formula();
            f.setFormula(formulaNode);
            simpleValue.addFormula(f);
        } else {
            bindColumns(fieldMeta, simpleValue, isNullable, true, path, mappings);
        }
    }

    private static void bindComment(Table table, TableMeta tableMeta) {
//FUTURE        if (tableMeta.getDescription() != null) table.setComment(tableMeta.getDescription());
    }

    public static void bindManyToOne(FieldMeta fieldMeta, ManyToOne manyToOne, String path,
                                     boolean isNullable, Mappings mappings) {

        bindColumnsOrFormula(fieldMeta, manyToOne, path, isNullable, mappings);
        initOuterJoinFetchSetting(fieldMeta, manyToOne);
        initLaziness(fieldMeta, manyToOne, mappings, true);

        FieldMeta fieldMetaRef = fieldMeta.getFieldRef();
        if (fieldMetaRef != null) {
            manyToOne.setReferencedPropertyName(fieldMetaRef.getName());
        }

        //TODO manyToOne.setReferencedEntityName(getEntityName(fieldMeta, mappings));
        manyToOne.setReferencedEntityName(fieldMetaRef.getTableName());

//SS        String embed = node.attributeValue("embed-xml");
//        // sometimes embed is set to the default value when not specified in the mapping,
//        // so can't seem to determine if an attribute was explicitly set;
//        // log a warning if embed has a value different from the default.
//        if (!StringHelper.isEmpty(embed) && !"true".equals(embed)) {
//            LOG.embedXmlAttributesNoLongerSupported();
//        }
//        manyToOne.setEmbedded(embed == null || "true".equals(embed));

//SS        String notFound = node.attributeValue("not-found");
//        manyToOne.setIgnoreNotFound("ignore".equals(notFound));

//SS        if (ukName != null && !manyToOne.isIgnoreNotFound()) {
//            if (!node.getName().equals("many-to-many")) { //TODO: really bad, evil hack to fix!!!
//                mappings.addSecondPass(new ManyToOneSecondPass(manyToOne));
//            }
//        }

//FUTURE        Attribute fkNode = node.attribute("foreign-key");
//        if (fkNode != null) manyToOne.setForeignKeyName(fkNode.getValue());

//FUTURE        String cascade = node.attributeValue("cascade");
//        if (cascade != null && cascade.indexOf("delete-orphan") >= 0) {
//            if (!manyToOne.isLogicalOneToOne()) {
//                throw new MappingException(
//                        "many-to-one attribute [" + path + "] does not support orphan delete as it is not unique"
//                );
//            }
//        }
    }

    //    public static void bindAny(Element node, Any any, boolean isNullable, Mappings mappings)
//            throws MappingException {
//        any.setIdentifierType(getTypeFromXML(node));
//        Attribute metaAttribute = node.attribute("meta-type");
//        if (metaAttribute != null) {
//            any.setMetaType(metaAttribute.getValue());
//
//            Iterator iter = node.elementIterator("meta-value");
//            if (iter.hasNext()) {
//                HashMap values = new HashMap();
//                org.hibernate.type.Type metaType = mappings.getTypeResolver().heuristicType(any.getMetaType());
//                while (iter.hasNext()) {
//                    Element metaValue = (Element) iter.next();
//                    try {
//                        Object value = ((DiscriminatorType) metaType).stringToObject(metaValue
//                                .attributeValue("value"));
//                        String entityName = getClassName(metaValue.attribute("class"), mappings);
//                        values.put(value, entityName);
//                    } catch (ClassCastException cce) {
//                        throw new MappingException("meta-type was not a DiscriminatorType: "
//                                + metaType.getName());
//                    } catch (Exception e) {
//                        throw new MappingException("could not interpret meta-value", e);
//                    }
//                }
//                any.setMetaValues(values);
//            }
//
//        }
//
//        bindColumns(node, any, isNullable, false, null, mappings);
//    }

    public static void bindOneToOne(FieldMeta fieldMeta, OneToOne oneToOne, String path, boolean isNullable,
                                    Mappings mappings) {

        bindColumns(fieldMeta, oneToOne, isNullable, false, null, mappings);

//SS        Attribute constrNode = node.attribute("constrained");
//        boolean constrained = constrNode != null && constrNode.getValue().equals("true");
//        oneToOne.setConstrained(constrained);

//        oneToOne.setForeignKeyType(constrained ?
//                ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT :
//                ForeignKeyDirection.FOREIGN_KEY_TO_PARENT);

        initOuterJoinFetchSetting(fieldMeta, oneToOne);
        initLaziness(fieldMeta, oneToOne, mappings, true);

//SS        String embed = node.attributeValue("embed-xml");
//        // sometimes embed is set to the default value when not specified in the mapping,
//        // so can't seem to determine if an attribute was explicitly set;
//        // log a warning if embed has a value different from the default.
//        if (!StringHelper.isEmpty(embed) && !"true".equals(embed)) {
//            LOG.embedXmlAttributesNoLongerSupported();
//        }
//        oneToOne.setEmbedded("true".equals(embed));

//FUTURE        Attribute fkNode = node.attribute("foreign-key");
//        if (fkNode != null) oneToOne.setForeignKeyName(fkNode.getValue());

        FieldMeta fieldMetaRef = fieldMeta.getFieldRef();
        if (fieldMetaRef != null) oneToOne.setReferencedPropertyName(fieldMetaRef.getName());

        oneToOne.setPropertyName(fieldMeta.getName());

        //TODO oneToOne.setReferencedEntityName(getEntityName(node, mappings));
        oneToOne.setReferencedEntityName(fieldMetaRef.getTableName());

//FUTURE        String cascade = node.attributeValue("cascade");
//        if (cascade != null && cascade.indexOf("delete-orphan") >= 0) {
//            if (oneToOne.isConstrained()) {
//                throw new MappingException(
//                        "one-to-one attribute [" + path + "] does not support orphan delete as it is constrained"
//                );
//            }
//        }
    }

    public static void bindOneToMany(FieldMeta fieldMeta, OneToMany oneToMany, Mappings mappings) {

//TODO        oneToMany.setReferencedEntityName(getEntityName(node, mappings));
        oneToMany.setReferencedEntityName(fieldMeta.getFieldRef().getTableName());

//SS        String embed = node.attributeValue("embed-xml");
//        // sometimes embed is set to the default value when not specified in the mapping,
//        // so can't seem to determine if an attribute was explicitly set;
//        // log a warning if embed has a value different from the default.
//        if (!StringHelper.isEmpty(embed) && !"true".equals(embed)) {
//            LOG.embedXmlAttributesNoLongerSupported();
//        }
//        oneToMany.setEmbedded(embed == null || "true".equals(embed));

//SS        String notFound = node.attributeValue("not-found");
        String notFound = null;
        oneToMany.setIgnoreNotFound("ignore".equals(notFound));

    }

    public static void bindColumn(FieldMeta fieldMeta, Column column, boolean isNullable) {
        Integer size = fieldMeta.getSize();
        if (size != null) column.setLength(size);

        //TODO scale only for numeric types
        if (size != null) column.setScale(size);

        Integer precision = fieldMeta.getPrecision();
        if (precision != null) column.setPrecision(precision);

        Boolean required = fieldMeta.isRequired();
        column.setNullable(required == null ? isNullable : required);

//TODO from constraint        Attribute unqNode = node.attribute("unique");
//        if (unqNode != null) column.setUnique(unqNode.getValue().equals("true"));

//FUTURE        column.setCheckConstraint(node.attributeValue("check"));

        column.setDefaultValue(fieldMeta.getInsertDefaultValue());

//FUTURE        Attribute typeNode = node.attribute("sql-type");
//        if (typeNode != null) column.setSqlType(typeNode.getValue());

//FUTURE        String customWrite = node.attributeValue("write");
//        if (customWrite != null && !customWrite.matches("[^?]*\\?[^?]*")) {
//            throw new MappingException("write expression must contain exactly one value placeholder ('?') character");
//        }
//        column.setCustomWrite(customWrite);
//        column.setCustomRead(node.attributeValue("read"));

        String description = fieldMeta.getDescription();
        if (description != null) column.setComment(description);

    }

    /**
     * Called for arrays and primitive arrays
     */
    public static void bindArray(FieldMeta fieldMeta, Array array, String prefix, String path,
                                 Mappings mappings, java.util.Map inheritedMetas) {

        bindCollection(fieldMeta, array, prefix, path, mappings, inheritedMetas);

//SS        Attribute att = node.attribute("element-class");
//        if (att != null) array.setElementClassName(getClassName(att, mappings));

    }

    //    private static Class reflectedPropertyClass(String className, String propertyName)
//            throws MappingException {
//        if (className == null) return null;
//        return ReflectHelper.reflectedPropertyClass(className, propertyName);
//    }
//
//    public static void bindComposite(Element node, Component component, String path,
//                                     boolean isNullable, Mappings mappings, java.util.Map inheritedMetas)
//            throws MappingException {
//        bindComponent(
//                node,
//                component,
//                null,
//                null,
//                path,
//                isNullable,
//                false,
//                mappings,
//                inheritedMetas,
//                false
//        );
//    }
//
    public static void bindCompositeId(FieldMeta fieldMeta, Component component,
                                       PersistentClass persistentClass, String propertyName, Mappings mappings,
                                       java.util.Map inheritedMetas) {

        component.setKey(true);

        String path = StringHelper.qualify(
                persistentClass.getEntityName(),
                propertyName == null ? "id" : propertyName);

        bindComponent(
                fieldMeta,
                component,
                persistentClass.getClassName(),
                propertyName,
                path,
                false,
                false,
//SS                node.attribute("class") == null
//                        && propertyName == null,
                mappings,
                inheritedMetas,
                false
        );

//SS        if ("true".equals(node.attributeValue("mapped"))) {
//            if (propertyName != null) {
//                throw new MappingException("cannot combine mapped=\"true\" with specified name");
//            }
//            Component mapper = new Component(mappings, persistentClass);
//            bindComponent(
//                    node,
//                    mapper,
//                    persistentClass.getClassName(),
//                    null,
//                    path,
//                    false,
//                    true,
//                    mappings,
//                    inheritedMetas,
//                    true
//            );
//            persistentClass.setIdentifierMapper(mapper);
//            Property property = new Property();
//            property.setName("_identifierMapper");
//            property.setNodeName("id");
//            property.setUpdateable(false);
//            property.setInsertable(false);
//            property.setValue(mapper);
//            property.setPropertyAccessorName("embedded");
//            persistentClass.addProperty(property);
//        }

    }

    public static void bindComponent(
            FieldMeta fieldMeta,
            Component component,
            String ownerClassName,
            String parentProperty,
            String path,
            boolean isNullable,
            boolean isEmbedded,
            Mappings mappings,
            java.util.Map inheritedMetas,
            boolean isIdentifierMapper) {

        component.setEmbedded(isEmbedded);
        component.setRoleName(path);

        inheritedMetas = getMetas(fieldMeta, inheritedMetas);
        component.setMetaAttributes(inheritedMetas);

//TODO        Attribute classNode = isIdentifierMapper ? null : node.attribute("class");
//        if (classNode != null) {
//            component.setComponentClassName(getClassName(classNode, mappings));
//        } else if ("dynamic-component".equals(node.getName())) {
//            component.setDynamic(true);
//        } else if (isEmbedded) {
//            // an "embedded" component (composite ids and unique)
//            // note that this does not handle nested components
//            if (component.getOwner().hasPojoRepresentation()) {
//                component.setComponentClassName(component.getOwner().getClassName());
//            } else {
//                component.setDynamic(true);
//            }
//        } else {
//            // HHtodo : again, how *should* this work for non-pojo entities?
//            if (component.getOwner().hasPojoRepresentation()) {
//                Class reflectedClass = reflectedPropertyClass(ownerClassName, parentProperty);
//                if (reflectedClass != null) {
//                    component.setComponentClassName(reflectedClass.getName());
//                }
//            } else {
//                component.setDynamic(true);
//            }
//        }
//
//        String nodeName = node.attributeValue("node");
//        if (nodeName == null) nodeName = node.attributeValue("name");
//        if (nodeName == null) nodeName = component.getOwner().getNodeName();
//        component.setNodeName(nodeName);
//
//        Iterator iter = node.elementIterator();
//        while (iter.hasNext()) {
//
//            Element subnode = (Element) iter.next();
//            String name = subnode.getName();
//            String propertyName = getPropertyName(subnode);
//            String subpath = propertyName == null ? null : StringHelper
//                    .qualify(path, propertyName);
//
//            CollectionType collectType = CollectionType.collectionTypeFromString(name);
//            Value value = null;
//            if (collectType != null) {
//                Collection collection = collectType.create(
//                        subnode,
//                        subpath,
//                        component.getOwner(),
//                        mappings, inheritedMetas
//                );
//                mappings.addCollection(collection);
//                value = collection;
//            } else if ("many-to-one".equals(name) || "key-many-to-one".equals(name)) {
//                value = new ManyToOne(mappings, component.getTable());
//                String relativePath;
//                if (isEmbedded) {
//                    relativePath = propertyName;
//                } else {
//                    relativePath = subpath.substring(component.getOwner().getEntityName().length() + 1);
//                }
//                bindManyToOne(subnode, (ManyToOne) value, relativePath, isNullable, mappings);
//            } else if ("one-to-one".equals(name)) {
//                value = new OneToOne(mappings, component.getTable(), component.getOwner());
//                String relativePath;
//                if (isEmbedded) {
//                    relativePath = propertyName;
//                } else {
//                    relativePath = subpath.substring(component.getOwner().getEntityName().length() + 1);
//                }
//                bindOneToOne(subnode, (OneToOne) value, relativePath, isNullable, mappings);
//            } else if ("any".equals(name)) {
//                value = new Any(mappings, component.getTable());
//                bindAny(subnode, (Any) value, isNullable, mappings);
//            } else if ("property".equals(name) || "key-property".equals(name)) {
//                value = new SimpleValue(mappings, component.getTable());
//                String relativePath;
//                if (isEmbedded) {
//                    relativePath = propertyName;
//                } else {
//                    relativePath = subpath.substring(component.getOwner().getEntityName().length() + 1);
//                }
//                bindSimpleValue(subnode, (SimpleValue) value, isNullable, relativePath, mappings);
//            } else if ("component".equals(name)
//                    || "dynamic-component".equals(name)
//                    || "nested-composite-element".equals(name)) {
//                value = new Component(mappings, component); // a nested composite element
//                bindComponent(
//                        subnode,
//                        (Component) value,
//                        component.getComponentClassName(),
//                        propertyName,
//                        subpath,
//                        isNullable,
//                        isEmbedded,
//                        mappings,
//                        inheritedMetas,
//                        isIdentifierMapper
//                );
//            } else if ("parent".equals(name)) {
//                component.setParentProperty(propertyName);
//            }
//
//            if (value != null) {
//                Property property = createProperty(value, propertyName, component
//                        .getComponentClassName(), subnode, mappings, inheritedMetas);
//                if (isIdentifierMapper) {
//                    property.setInsertable(false);
//                    property.setUpdateable(false);
//                }
//                component.addProperty(property);
//            }
//        }
//
//        if ("true".equals(node.attributeValue("unique"))) {
//            iter = component.getColumnIterator();
//            ArrayList cols = new ArrayList();
//            while (iter.hasNext()) {
//                cols.add(iter.next());
//            }
//            component.getOwner().getTable().createUniqueKey(cols);
//        }
//
//        iter = node.elementIterator("tuplizer");
//        while (iter.hasNext()) {
//            final Element tuplizerElem = (Element) iter.next();
//            EntityMode mode = EntityMode.parse(tuplizerElem.attributeValue("entity-mode"));
//            component.addTuplizer(mode, tuplizerElem.attributeValue("class"));
//        }
    }

//    public static String getTypeFromXML(Element node) throws MappingException {
//        // HHTODO: handle TypeDefs
//        Attribute typeNode = node.attribute("type");
//        if (typeNode == null) typeNode = node.attribute("id-type"); // for an any
//        if (typeNode == null) return null; // we will have to use reflection
//        return typeNode.getValue();
//    }

    private static void initOuterJoinFetchSetting(FieldMeta fieldMeta, Fetchable model) {
//FUTURE        Attribute fetchNode = node.attribute("fetch");
        String fetchNode = null;
        FetchMode fetchStyle = null;
        boolean lazy = true;
        if (fetchNode == null) {
//FUTURE            Attribute jfNode = node.attribute("outer-join");
            String jfNode = null;
            if (jfNode == null) {
                if (fieldMeta.getAssociationType() == FieldAssociationType.MANY_TO_MANY) {
                    //NOTE SPECIAL CASE:
                    // default to join and non-lazy for the "second join"
                    // of the many-to-many
                    lazy = false;
                    fetchStyle = FetchMode.JOIN;
                } else if (fieldMeta.getAssociationType() == FieldAssociationType.ONE_TO_ONE) {
                    //NOTE SPECIAL CASE:
                    // one-to-one constrained=false cannot be proxied,
                    // so default to join and non-lazy
                    lazy = ((OneToOne) model).isConstrained();
                    fetchStyle = lazy ? FetchMode.DEFAULT : FetchMode.JOIN;
                } else {
                    fetchStyle = FetchMode.DEFAULT;
                }
            } else {
                // use old (HB 2.1) defaults if outer-join is specified
//FUTURE                String eoj = jfNode.getValue();
//                if ("auto".equals(eoj)) {
//                    fetchStyle = FetchMode.DEFAULT;
//                } else {
//                    boolean join = "true".equals(eoj);
//                    fetchStyle = join ? FetchMode.JOIN : FetchMode.SELECT;
//                }
            }
        } else {
//FUTURE            boolean join = "join".equals(fetchNode.getValue());
//            //lazy = !join;
//            fetchStyle = join ? FetchMode.JOIN : FetchMode.SELECT;
        }
        model.setFetchMode(fetchStyle);
        model.setLazy(lazy);
    }

    private static void makeIdentifier(FieldMeta fieldMeta, SimpleValue model, Mappings mappings) {

        // GENERATOR
//TODO !        Element subnode = node.element("generator");
//        if (subnode != null) {
//            final String generatorClass = subnode.attributeValue("class");
//            model.setIdentifierGeneratorStrategy(generatorClass);
//
//            Properties params = new Properties();
//            // YUCK!  but cannot think of a clean way to do this given the string-config based scheme
//            params.put(PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER, mappings.getObjectNameNormalizer());
//
//            if (mappings.getSchemaName() != null) {
//                params.setProperty(
//                        PersistentIdentifierGenerator.SCHEMA,
//                        mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(mappings.getSchemaName())
//                );
//            }
//            if (mappings.getCatalogName() != null) {
//                params.setProperty(
//                        PersistentIdentifierGenerator.CATALOG,
//                        mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(mappings.getCatalogName())
//                );
//            }
//
//            Iterator iter = subnode.elementIterator("param");
//            while (iter.hasNext()) {
//                Element childNode = (Element) iter.next();
//                params.setProperty(childNode.attributeValue("name"), childNode.getTextTrim());
//            }
//
//            model.setIdentifierGeneratorProperties(params);
//        }

        model.getTable().setIdentifierValue(model);

        // ID UNSAVED-VALUE
//FUTURE        Attribute nullValueNode = node.attribute("unsaved-value");
//        if (nullValueNode != null) {
//            model.setNullValue(nullValueNode.getValue());
//        } else {
//            if ("assigned".equals(model.getIdentifierGeneratorStrategy())) {
//                model.setNullValue("undefined");
//            } else {
//                model.setNullValue(null);
//            }
//        }
    }

    //    private static final void makeVersion(Element node, SimpleValue model) {
//
//        // VERSION UNSAVED-VALUE
//        Attribute nullValueNode = node.attribute("unsaved-value");
//        if (nullValueNode != null) {
//            model.setNullValue(nullValueNode.getValue());
//        } else {
//            model.setNullValue("undefined");
//        }
//
//    }
//
    protected static void createClassProperties(TableMeta tableMeta, PersistentClass persistentClass,
                                                Mappings mappings, java.util.Map inheritedMetas) {
        createClassProperties(tableMeta, persistentClass, mappings, inheritedMetas, null, true, true, false);
    }

    protected static void createClassProperties(TableMeta tableMeta, PersistentClass persistentClass,
                                                Mappings mappings, java.util.Map inheritedMetas, UniqueKey uniqueKey,
                                                boolean mutable, boolean nullable, boolean naturalId) {

        String entityName = persistentClass.getEntityName();
        Table table = persistentClass.getTable();

        for (FieldMeta fieldMeta : tableMeta.getFields()) {
            String name = null;
            if (List.class.isAssignableFrom(fieldMeta.getType())) {
                name = "list";
            } else if (Set.class.isAssignableFrom(fieldMeta.getType())) {
                name = "set";
            } else if (Map.class.isAssignableFrom(fieldMeta.getType())) {
                name = "map";
            }
            String propertyName = fieldMeta.getName();

            CollectionType collectType = CollectionType.collectionTypeFromString(name);
            Value value = null;
            if (collectType != null) {
                Collection collection = collectType.create(
                        fieldMeta,
                        StringHelper.qualify(entityName, propertyName),
                        persistentClass,
                        mappings, inheritedMetas
                );
                mappings.addCollection(collection);
                value = collection;
            } else if (fieldMeta.getAssociationType() == FieldAssociationType.MANY_TO_ONE) {
                value = new ManyToOne(mappings, table);
                bindManyToOne(fieldMeta, (ManyToOne) value, propertyName, nullable, mappings);
//FUTURE            } else if ("any".equals(name)) {
//                value = new Any(mappings, table);
//                bindAny(subnode, (Any) value, nullable, mappings);
            } else if (fieldMeta.getAssociationType() == FieldAssociationType.ONE_TO_ONE) {
                value = new OneToOne(mappings, table, persistentClass);
                bindOneToOne(fieldMeta, (OneToOne) value, propertyName, true, mappings);
            } else if (!fieldMeta.isProcessed()) {
                value = new SimpleValue(mappings, table);
                bindSimpleValue(fieldMeta, (SimpleValue) value, nullable, propertyName, mappings);
//FUTURE            } else if ("component".equals(name)
//                    || "dynamic-component".equals(name)
//                    || "properties".equals(name)) {
//                String subpath = StringHelper.qualify(entityName, propertyName);
//                value = new Component(mappings, persistentClass);
//
//                bindComponent(
//                        subnode,
//                        (Component) value,
//                        persistentClass.getClassName(),
//                        propertyName,
//                        subpath,
//                        true,
//                        "properties".equals(name),
//                        mappings,
//                        inheritedMetas,
//                        false
//                );
//FUTURE            } else if ("join".equals(name)) {
//                Join join = new Join();
//                join.setPersistentClass(persistentClass);
//                bindJoin(subnode, join, mappings, inheritedMetas);
//                persistentClass.addJoin(join);
//FUTURE            } else if ("subclass".equals(name)) {
//                handleSubclass(persistentClass, mappings, subnode, inheritedMetas);
//FUTURE            } else if ("joined-subclass".equals(name)) {
//                handleJoinedSubclass(persistentClass, mappings, subnode, inheritedMetas);
//FUTURE            } else if ("union-subclass".equals(name)) {
//                handleUnionSubclass(persistentClass, mappings, subnode, inheritedMetas);
//FUTURE            } else if ("filter".equals(name)) {
//                parseFilter(subnode, persistentClass, mappings);
//FUTURE            } else if ("natural-id".equals(name)) {
//                UniqueKey uk = new UniqueKey();
//                uk.setName("_UniqueKey");
//                uk.setTable(table);
//                //by default, natural-ids are "immutable" (constant)
//                boolean mutableId = "true".equals(subnode.attributeValue("mutable"));
//                createClassProperties(
//                        subnode,
//                        persistentClass,
//                        mappings,
//                        inheritedMetas,
//                        uk,
//                        mutableId,
//                        false,
//                        true
//                );
//                table.addUniqueKey(uk);
//FUTURE            } else if ("query".equals(name)) {
//                bindNamedQuery(subnode, persistentClass.getEntityName(), mappings);
//FUTURE            } else if ("sql-query".equals(name)) {
//                bindNamedSQLQuery(subnode, persistentClass.getEntityName(), mappings);
//FUTURE            } else if ("resultset".equals(name)) {
//                bindResultSetMappingDefinition(subnode, persistentClass.getEntityName(), mappings);
            }

            if (value != null) {
                final Property property = createProperty(
                        value,
                        propertyName,
                        persistentClass.getClassName(),
                        fieldMeta,
                        mappings,
                        inheritedMetas
                );
                if (!mutable) {
                    property.setUpdateable(false);
                }
                if (naturalId) {
                    property.setNaturalIdentifier(true);
                }
                persistentClass.addProperty(property);
                if (uniqueKey != null) {
                    uniqueKey.addColumns(property.getColumnIterator());
                }
            }

        }
    }

    private static Property createProperty(
            final Value value,
            final String propertyName,
            final String className,
            final FieldMeta fieldMeta,
            final Mappings mappings,
            java.util.Map inheritedMetas) {

        value.setTypeUsingReflection(className, propertyName);

        // this is done here 'cos we might only know the type here (ugly!)
        // HHTODO: improve this a lot:
        if (value instanceof ToOne) {
            ToOne toOne = (ToOne) value;
            String propertyRef = toOne.getReferencedPropertyName();
            if (propertyRef != null) {
                mappings.addUniquePropertyReference(toOne.getReferencedEntityName(), propertyRef);
            }
        } else if (value instanceof Collection) {
            Collection coll = (Collection) value;
            String propertyRef = coll.getReferencedPropertyName();
            // not necessarily a *unique* property reference
            if (propertyRef != null) {
                mappings.addPropertyReference(coll.getOwnerEntityName(), propertyRef);
            }
        }

        value.createForeignKey();
        Property prop = new Property();
        prop.setValue(value);
        bindProperty(fieldMeta, prop, mappings, inheritedMetas);
        return prop;
    }

//    private static void handleUnionSubclass(PersistentClass model, Mappings mappings,
//                                            Element subnode, java.util.Map inheritedMetas) throws MappingException {
//        UnionSubclass subclass = new UnionSubclass(model);
//        bindUnionSubclass(subnode, subclass, mappings, inheritedMetas);
//        model.addSubclass(subclass);
//        mappings.addClass(subclass);
//    }
//
//    private static void handleJoinedSubclass(PersistentClass model, Mappings mappings,
//                                             Element subnode, java.util.Map inheritedMetas) throws MappingException {
//        JoinedSubclass subclass = new JoinedSubclass(model);
//        bindJoinedSubclass(subnode, subclass, mappings, inheritedMetas);
//        model.addSubclass(subclass);
//        mappings.addClass(subclass);
//    }
//
//    private static void handleSubclass(PersistentClass model, Mappings mappings, Element subnode,
//                                       java.util.Map inheritedMetas) throws MappingException {
//        Subclass subclass = new SingleTableSubclass(model);
//        bindSubclass(subnode, subclass, mappings, inheritedMetas);
//        model.addSubclass(subclass);
//        mappings.addClass(subclass);
//    }

    /**
     * Called for Lists, arrays, primitive arrays
     */
    public static void bindListSecondPass(FieldMeta fieldMeta, List list, java.util.Map classes,
                                          Mappings mappings, java.util.Map inheritedMetas) {

        bindCollectionSecondPass(fieldMeta, list, classes, mappings, inheritedMetas);

//FUTURE        Element subnode = node.element("list-index");
//        if (subnode == null) subnode = node.element("index");
//        SimpleValue iv = new SimpleValue(mappings, list.getCollectionTable());
//        bindSimpleValue(
//                subnode,
//                iv,
//                list.isOneToMany(),
//                IndexedCollection.DEFAULT_INDEX_COLUMN_NAME,
//                mappings
//        );
//        iv.setTypeName("integer");
//        list.setIndex(iv);
//        String baseIndex = subnode.attributeValue("base");
//        if (baseIndex != null) list.setBaseIndex(Integer.parseInt(baseIndex));
//        list.setIndexNodeName(subnode.attributeValue("node"));

        if (list.isOneToMany() && !list.getKey().isNullable() && !list.isInverse()) {
            String entityName = ((OneToMany) list.getElement()).getReferencedEntityName();
            PersistentClass referenced = mappings.getClass(entityName);
            IndexBackref ib = new IndexBackref();
            ib.setName('_' + list.getOwnerEntityName() + "." + fieldMeta.getName() + "IndexBackref");
            ib.setUpdateable(false);
            ib.setSelectable(false);
            ib.setCollectionRole(list.getRole());
            ib.setEntityName(list.getOwner().getEntityName());
            ib.setValue(list.getIndex());
            // ( (Column) ( (SimpleValue) ic.getIndex() ).getColumnIterator().next()
            // ).setNullable(false);
            referenced.addProperty(ib);
        }
    }

    public static void bindIdentifierCollectionSecondPass(FieldMeta fieldMeta,
                                                          IdentifierCollection collection, java.util.Map persistentClasses, Mappings mappings,
                                                          java.util.Map inheritedMetas) {

        bindCollectionSecondPass(fieldMeta, collection, persistentClasses, mappings, inheritedMetas);

//SS        Element subnode = node.element("collection-id");
//        SimpleValue id = new SimpleValue(mappings, collection.getCollectionTable());
//        bindSimpleValue(
//                subnode,
//                id,
//                false,
//                IdentifierCollection.DEFAULT_IDENTIFIER_COLUMN_NAME,
//                mappings
//        );
//        collection.setIdentifier(id);
//        makeIdentifier(subnode, id, mappings);

    }

    /**
     * Called for Maps
     */
    public static void bindMapSecondPass(FieldMeta fieldMeta, org.hibernate.mapping.Map map, java.util.Map classes,
                                         Mappings mappings, java.util.Map inheritedMetas) {

        bindCollectionSecondPass(fieldMeta, map, classes, mappings, inheritedMetas);

//        Iterator iter = node.elementIterator();
//TODO        while (iter.hasNext()) {
//            Element subnode = (Element) iter.next();
//            String name = subnode.getName();
//
//            if ("index".equals(name) || "map-key".equals(name)) {
//                SimpleValue value = new SimpleValue(mappings, map.getCollectionTable());
//                bindSimpleValue(
//                        subnode,
//                        value,
//                        map.isOneToMany(),
//                        IndexedCollection.DEFAULT_INDEX_COLUMN_NAME,
//                        mappings
//                );
//                if (!value.isTypeSpecified()) {
//                    throw new MappingException("map index element must specify a type: "
//                            + map.getRole());
//                }
//                map.setIndex(value);
//                map.setIndexNodeName(subnode.attributeValue("node"));
//            } else if ("index-many-to-many".equals(name) || "map-key-many-to-many".equals(name)) {
//                ManyToOne mto = new ManyToOne(mappings, map.getCollectionTable());
//                bindManyToOne(
//                        subnode,
//                        mto,
//                        IndexedCollection.DEFAULT_INDEX_COLUMN_NAME,
//                        map.isOneToMany(),
//                        mappings
//                );
//                map.setIndex(mto);
//
//            } else if ("composite-index".equals(name) || "composite-map-key".equals(name)) {
//                Component component = new Component(mappings, map);
//                bindComposite(
//                        subnode,
//                        component,
//                        map.getRole() + ".index",
//                        map.isOneToMany(),
//                        mappings,
//                        inheritedMetas
//                );
//                map.setIndex(component);
//            } else if ("index-many-to-any".equals(name)) {
//                Any any = new Any(mappings, map.getCollectionTable());
//                bindAny(subnode, any, map.isOneToMany(), mappings);
//                map.setIndex(any);
//            }
//        }

        // HHTODO: this is a bit of copy/paste from IndexedCollection.createPrimaryKey()
        boolean indexIsFormula = false;
//SS        Iterator colIter = map.getIndex().getColumnIterator();
//        while (colIter.hasNext()) {
//            if (((Selectable) colIter.next()).isFormula()) indexIsFormula = true;
//        }

        if (map.isOneToMany() && !map.getKey().isNullable() && !map.isInverse() && !indexIsFormula) {
            String entityName = ((OneToMany) map.getElement()).getReferencedEntityName();
            PersistentClass referenced = mappings.getClass(entityName);
            IndexBackref ib = new IndexBackref();
            ib.setName('_' + map.getOwnerEntityName() + "." + fieldMeta.getName() + "IndexBackref");
            ib.setUpdateable(false);
            ib.setSelectable(false);
            ib.setCollectionRole(map.getRole());
            ib.setEntityName(map.getOwner().getEntityName());
            ib.setValue(map.getIndex());
            // ( (Column) ( (SimpleValue) ic.getIndex() ).getColumnIterator().next()
            // ).setNullable(false);
            referenced.addProperty(ib);
        }
    }

    /**
     * Called for all collections
     */
    public static void bindCollectionSecondPass(FieldMeta fieldMeta, Collection collection,
                                                java.util.Map persistentClasses, Mappings mappings, java.util.Map inheritedMetas) {

        if (collection.isOneToMany()) {
            OneToMany oneToMany = (OneToMany) collection.getElement();
            String assocClass = oneToMany.getReferencedEntityName();
            PersistentClass persistentClass = (PersistentClass) persistentClasses.get(assocClass);
//SS            if (persistentClass == null) {
//                throw new MappingException("Association references unmapped class: " + assocClass);
//            }
            oneToMany.setAssociatedClass(persistentClass);
            collection.setCollectionTable(persistentClass.getTable());

//SS            if (LOG.isDebugEnabled()) {
//                LOG.debugf("Mapping collection: %s -> %s", collection.getRole(), collection.getCollectionTable().getName());
//            }
        }

        // CHECK
//FUTURE        Attribute chNode = node.attribute("check");
//        if (chNode != null) {
//            collection.getCollectionTable().addCheckConstraint(chNode.getValue());
//        }

        // contained elements:
//TODO        Iterator iter = node.elementIterator();
//        while (iter.hasNext()) {
//            Element subnode = (Element) iter.next();
//            String name = subnode.getName();
//
//            if ("key".equals(name)) {
//                KeyValue keyVal;
//                String propRef = collection.getReferencedPropertyName();
//                if (propRef == null) {
//                    keyVal = collection.getOwner().getIdentifier();
//                } else {
//                    keyVal = (KeyValue) collection.getOwner().getRecursiveProperty(propRef).getValue();
//                }
//                SimpleValue key = new DependantValue(mappings, collection.getCollectionTable(), keyVal);
//                key.setCascadeDeleteEnabled("cascade"
//                        .equals(subnode.attributeValue("on-delete")));
//                bindSimpleValue(
//                        subnode,
//                        key,
//                        collection.isOneToMany(),
//                        Collection.DEFAULT_KEY_COLUMN_NAME,
//                        mappings
//                );
//                collection.setKey(key);
//
//                Attribute notNull = subnode.attribute("not-null");
//                ((DependantValue) key).setNullable(notNull == null
//                        || notNull.getValue().equals("false"));
//                Attribute updateable = subnode.attribute("update");
//                ((DependantValue) key).setUpdateable(updateable == null
//                        || updateable.getValue().equals("true"));
//
//            } else if ("element".equals(name)) {
//                SimpleValue elt = new SimpleValue(mappings, collection.getCollectionTable());
//                collection.setElement(elt);
//                bindSimpleValue(
//                        subnode,
//                        elt,
//                        true,
//                        Collection.DEFAULT_ELEMENT_COLUMN_NAME,
//                        mappings
//                );
//            } else if ("many-to-many".equals(name)) {
//                ManyToOne element = new ManyToOne(mappings, collection.getCollectionTable());
//                collection.setElement(element);
//                bindManyToOne(
//                        subnode,
//                        element,
//                        Collection.DEFAULT_ELEMENT_COLUMN_NAME,
//                        false,
//                        mappings
//                );
//                bindManyToManySubelements(collection, subnode, mappings);
//            } else if ("composite-element".equals(name)) {
//                Component element = new Component(mappings, collection);
//                collection.setElement(element);
//                bindComposite(
//                        subnode,
//                        element,
//                        collection.getRole() + ".element",
//                        true,
//                        mappings,
//                        inheritedMetas
//                );
//FUTURE            } else if ("many-to-any".equals(name)) {
//                Any element = new Any(mappings, collection.getCollectionTable());
//                collection.setElement(element);
//                bindAny(subnode, element, true, mappings);
//FUTURE            } else if ("cache".equals(name)) {
//                collection.setCacheConcurrencyStrategy(subnode.attributeValue("usage"));
//                collection.setCacheRegionName(subnode.attributeValue("region"));
//            }

//SS            String nodeName = subnode.attributeValue("node");
//            if (nodeName != null) collection.setElementNodeName(nodeName);

//        }

        if (collection.isOneToMany()
                && !collection.isInverse()
                && !collection.getKey().isNullable()) {
            // for non-inverse one-to-many, with a not-null fk, add a backref!
            String entityName = ((OneToMany) collection.getElement()).getReferencedEntityName();
            PersistentClass referenced = mappings.getClass(entityName);
            Backref prop = new Backref();
            prop.setName('_' + collection.getOwnerEntityName() + "." + fieldMeta.getName() + "Backref");
            prop.setUpdateable(false);
            prop.setSelectable(false);
            prop.setCollectionRole(collection.getRole());
            prop.setEntityName(collection.getOwner().getEntityName());
            prop.setValue(collection.getKey());
            referenced.addProperty(prop);
        }
    }

//    private static void bindManyToManySubelements(
//            Collection collection,
//            Element manyToManyNode,
//            Mappings model) throws MappingException {
//        // Bind the where
//        Attribute where = manyToManyNode.attribute("where");
//        String whereCondition = where == null ? null : where.getValue();
//        collection.setManyToManyWhere(whereCondition);
//
//        // Bind the order-by
//        Attribute order = manyToManyNode.attribute("order-by");
//        String orderFragment = order == null ? null : order.getValue();
//        collection.setManyToManyOrdering(orderFragment);
//
//        // Bind the filters
//        Iterator filters = manyToManyNode.elementIterator("filter");
//        if ((filters.hasNext() || whereCondition != null) &&
//                collection.getFetchMode() == FetchMode.JOIN &&
//                collection.getElement().getFetchMode() != FetchMode.JOIN) {
//            throw new MappingException(
//                    "many-to-many defining filter or where without join fetching " +
//                            "not valid within collection using join fetching [" + collection.getRole() + "]"
//            );
//        }
//        while (filters.hasNext()) {
//            final Element filterElement = (Element) filters.next();
//            final String name = filterElement.attributeValue("name");
//            String condition = filterElement.getTextTrim();
//            if (StringHelper.isEmpty(condition)) condition = filterElement.attributeValue("condition");
//            if (StringHelper.isEmpty(condition)) {
//                condition = model.getFilterDefinition(name).getDefaultFilterCondition();
//            }
//            if (condition == null) {
//                throw new MappingException("no filter condition found for filter: " + name);
//            }
//            Iterator aliasesIterator = filterElement.elementIterator("aliases");
//            java.util.Map<String, String> aliasTables = new HashMap<String, String>();
//            while (aliasesIterator.hasNext()) {
//                Element alias = (Element) aliasesIterator.next();
//                aliasTables.put(alias.attributeValue("alias"), alias.attributeValue("table"));
//            }
//            if (LOG.isDebugEnabled()) {
//                LOG.debugf("Applying many-to-many filter [%s] as [%s] to role [%s]", name, condition, collection.getRole());
//            }
//            String autoAliasInjectionText = filterElement.attributeValue("autoAliasInjection");
//            boolean autoAliasInjection = StringHelper.isEmpty(autoAliasInjectionText) ? true : Boolean.parseBoolean(autoAliasInjectionText);
//            collection.addManyToManyFilter(name, condition, autoAliasInjection, aliasTables, null);
//        }
//    }
//
//    public static final FlushMode getFlushMode(String flushMode) {
//        if (flushMode == null) {
//            return null;
//        } else if ("auto".equals(flushMode)) {
//            return FlushMode.AUTO;
//        } else if ("commit".equals(flushMode)) {
//            return FlushMode.COMMIT;
//        } else if ("never".equals(flushMode)) {
//            return FlushMode.NEVER;
//        } else if ("manual".equals(flushMode)) {
//            return FlushMode.MANUAL;
//        } else if ("always".equals(flushMode)) {
//            return FlushMode.ALWAYS;
//        } else {
//            throw new MappingException("unknown flushmode");
//        }
//    }
//
//    private static void bindNamedQuery(Element queryElem, String path, Mappings mappings) {
//        String queryName = queryElem.attributeValue("name");
//        if (path != null) queryName = path + '.' + queryName;
//        String query = queryElem.getText();
//        LOG.debugf("Named query: %s -> %s", queryName, query);
//
//        boolean cacheable = "true".equals(queryElem.attributeValue("cacheable"));
//        String region = queryElem.attributeValue("cache-region");
//        Attribute tAtt = queryElem.attribute("timeout");
//        Integer timeout = tAtt == null ? null : Integer.valueOf(tAtt.getValue());
//        Attribute fsAtt = queryElem.attribute("fetch-size");
//        Integer fetchSize = fsAtt == null ? null : Integer.valueOf(fsAtt.getValue());
//        Attribute roAttr = queryElem.attribute("read-only");
//        boolean readOnly = roAttr != null && "true".equals(roAttr.getValue());
//        Attribute cacheModeAtt = queryElem.attribute("cache-mode");
//        String cacheMode = cacheModeAtt == null ? null : cacheModeAtt.getValue();
//        Attribute cmAtt = queryElem.attribute("comment");
//        String comment = cmAtt == null ? null : cmAtt.getValue();
//
//        NamedQueryDefinition namedQuery = new NamedQueryDefinition(
//                queryName,
//                query,
//                cacheable,
//                region,
//                timeout,
//                fetchSize,
//                getFlushMode(queryElem.attributeValue("flush-mode")),
//                getCacheMode(cacheMode),
//                readOnly,
//                comment,
//                getParameterTypes(queryElem)
//        );
//
//        mappings.addQuery(namedQuery.getName(), namedQuery);
//    }
//
//    public static CacheMode getCacheMode(String cacheMode) {
//        if (cacheMode == null) return null;
//        if ("get".equals(cacheMode)) return CacheMode.GET;
//        if ("ignore".equals(cacheMode)) return CacheMode.IGNORE;
//        if ("normal".equals(cacheMode)) return CacheMode.NORMAL;
//        if ("put".equals(cacheMode)) return CacheMode.PUT;
//        if ("refresh".equals(cacheMode)) return CacheMode.REFRESH;
//        throw new MappingException("Unknown Cache Mode: " + cacheMode);
//    }
//
//    public static java.util.Map getParameterTypes(Element queryElem) {
//        java.util.Map result = new java.util.LinkedHashMap();
//        Iterator iter = queryElem.elementIterator("query-param");
//        while (iter.hasNext()) {
//            Element element = (Element) iter.next();
//            result.put(element.attributeValue("name"), element.attributeValue("type"));
//        }
//        return result;
//    }
//
//    private static void bindResultSetMappingDefinition(Element resultSetElem, String path, Mappings mappings) {
//        mappings.addSecondPass(new ResultSetMappingSecondPass(resultSetElem, path, mappings));
//    }
//
//    private static void bindNamedSQLQuery(Element queryElem, String path, Mappings mappings) {
//        mappings.addSecondPass(new NamedSQLQuerySecondPass(queryElem, path, mappings));
//    }
//
//    private static String getPropertyName(Element node) {
//        return node.attributeValue("name");
//    }
//
//    private static PersistentClass getSuperclass(Mappings mappings, Element subnode)
//            throws MappingException {
//        String extendsName = subnode.attributeValue("extends");
//        PersistentClass superModel = mappings.getClass(extendsName);
//        if (superModel == null) {
//            String qualifiedExtendsName = getClassName(extendsName, mappings);
//            superModel = mappings.getClass(qualifiedExtendsName);
//        }
//
//        if (superModel == null) {
//            throw new MappingException("Cannot extend unmapped class " + extendsName);
//        }
//        return superModel;
//    }

    static class CollectionSecondPass extends org.hibernate.cfg.CollectionSecondPass {
        FieldMeta fieldMeta;
        Collection collection;
        Mappings mappings;

        CollectionSecondPass(FieldMeta fieldMeta, Mappings mappings, Collection collection, java.util.Map inheritedMetas) {
            super(mappings, collection, inheritedMetas);
            this.fieldMeta = fieldMeta;
            this.collection = collection;
            this.mappings = mappings;
        }

        public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas) {
            bindCollectionSecondPass(
                    fieldMeta,
                    collection,
                    persistentClasses,
                    mappings,
                    inheritedMetas
            );
        }
    }

    static class IdentifierCollectionSecondPass extends CollectionSecondPass {
        Collection collection;
        Mappings mappings;

        IdentifierCollectionSecondPass(FieldMeta fieldMeta, Mappings mappings, Collection collection, java.util.Map inheritedMetas) {
            super(fieldMeta, mappings, collection, inheritedMetas);
            this.collection = collection;
            this.mappings = mappings;
        }

        public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas) {
            bindIdentifierCollectionSecondPass(
                    fieldMeta,
                    (IdentifierCollection) collection,
                    persistentClasses,
                    mappings,
                    inheritedMetas
            );
        }

    }

    static class MapSecondPass extends CollectionSecondPass {
        org.hibernate.mapping.Map collection;
        Mappings mappings;

        MapSecondPass(FieldMeta fieldMeta, Mappings mappings, org.hibernate.mapping.Map collection, java.util.Map inheritedMetas) {
            super(fieldMeta, mappings, collection, inheritedMetas);
            this.collection = collection;
            this.mappings = mappings;
        }

        public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas) {
            bindMapSecondPass(
                    fieldMeta,
                    (org.hibernate.mapping.Map) collection,
                    persistentClasses,
                    mappings,
                    inheritedMetas
            );
        }

    }


    static class ManyToOneSecondPass implements SecondPass {
        private final ManyToOne manyToOne;

        ManyToOneSecondPass(ManyToOne manyToOne) {
            this.manyToOne = manyToOne;
        }

        public void doSecondPass(java.util.Map persistentClasses) {
            manyToOne.createPropertyRefConstraints(persistentClasses);
        }

    }

    static class ListSecondPass extends CollectionSecondPass {
        org.hibernate.mapping.List collection;
        Mappings mappings;

        ListSecondPass(FieldMeta fieldMeta, Mappings mappings, org.hibernate.mapping.List collection, java.util.Map inheritedMetas) {
            super(fieldMeta, mappings, collection, inheritedMetas);
            this.collection = collection;
            this.mappings = mappings;
        }

        public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas) {
            bindListSecondPass(
                    fieldMeta,
                    collection,
                    persistentClasses,
                    mappings,
                    inheritedMetas
            );
        }

    }

    // This inner class implements a case statement....perhaps im being a bit over-clever here
    abstract static class CollectionType {
        private String xmlTag;

        public abstract Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                          Mappings mappings, java.util.Map inheritedMetas);

        CollectionType(String xmlTag) {
            this.xmlTag = xmlTag;
        }

        public String toString() {
            return xmlTag;
        }

        private static final CollectionType MAP = new CollectionType("map") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                org.hibernate.mapping.Map map = new org.hibernate.mapping.Map(mappings, owner);
                bindCollection(fieldMeta, map, owner.getEntityName(), path, mappings, inheritedMetas);
                return map;
            }
        };
        private static final CollectionType SET = new CollectionType("set") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                org.hibernate.mapping.Set set = new org.hibernate.mapping.Set(mappings, owner);
                bindCollection(fieldMeta, set, owner.getEntityName(), path, mappings, inheritedMetas);
                return set;
            }
        };
        private static final CollectionType LIST = new CollectionType("list") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                org.hibernate.mapping.List list = new org.hibernate.mapping.List(mappings, owner);
                bindCollection(fieldMeta, list, owner.getEntityName(), path, mappings, inheritedMetas);
                return list;
            }
        };
        private static final CollectionType BAG = new CollectionType("bag") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                Bag bag = new Bag(mappings, owner);
                bindCollection(fieldMeta, bag, owner.getEntityName(), path, mappings, inheritedMetas);
                return bag;
            }
        };
        private static final CollectionType IDBAG = new CollectionType("idbag") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                IdentifierBag bag = new IdentifierBag(mappings, owner);
                bindCollection(fieldMeta, bag, owner.getEntityName(), path, mappings, inheritedMetas);
                return bag;
            }
        };
        private static final CollectionType ARRAY = new CollectionType("array") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                Array array = new Array(mappings, owner);
                bindArray(fieldMeta, array, owner.getEntityName(), path, mappings, inheritedMetas);
                return array;
            }
        };
        private static final CollectionType PRIMITIVE_ARRAY = new CollectionType("primitive-array") {
            public Collection create(FieldMeta fieldMeta, String path, PersistentClass owner,
                                     Mappings mappings, java.util.Map inheritedMetas) {
                PrimitiveArray array = new PrimitiveArray(mappings, owner);
                bindArray(fieldMeta, array, owner.getEntityName(), path, mappings, inheritedMetas);
                return array;
            }
        };
        private static final HashMap INSTANCES = new HashMap();

        static {
            INSTANCES.put(MAP.toString(), MAP);
            INSTANCES.put(BAG.toString(), BAG);
            INSTANCES.put(IDBAG.toString(), IDBAG);
            INSTANCES.put(SET.toString(), SET);
            INSTANCES.put(LIST.toString(), LIST);
            INSTANCES.put(ARRAY.toString(), ARRAY);
            INSTANCES.put(PRIMITIVE_ARRAY.toString(), PRIMITIVE_ARRAY);
        }

        public static CollectionType collectionTypeFromString(String xmlTagName) {
            return (CollectionType) INSTANCES.get(xmlTagName);
        }
    }

    private static int getOptimisticLockMode(String olMode) {

        if ("version".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_VERSION;
        } else if ("dirty".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_DIRTY;
        } else if ("all".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_ALL;
        } else if ("none".equals(olMode)) {
            return Versioning.OPTIMISTIC_LOCK_NONE;
        }
        return Versioning.OPTIMISTIC_LOCK_VERSION;
    }

    private static final java.util.Map getMetas(DatabaseModel model, java.util.Map inheritedMeta) {
        return getMetas(model, inheritedMeta, false);
    }

    private static final java.util.Map getMetas(TableMeta tableMeta, java.util.Map inheritedMeta) {
        return getMetas(tableMeta, inheritedMeta, false);
    }

    private static final java.util.Map getMetas(FieldMeta fieldMeta, java.util.Map inheritedMeta) {
        return getMetas(fieldMeta, inheritedMeta, false);
    }

    private static Map getMetas(TableMeta tableMeta, Map inheritedMeta, boolean onlyInheritable) {
        java.util.Map map = new HashMap();
        map.putAll(inheritedMeta);
        // see below
        return map;
    }

    private static Map getMetas(FieldMeta fieldMeta, Map inheritedMeta, boolean onlyInheritable) {
        java.util.Map map = new HashMap();
        map.putAll(inheritedMeta);
        // see below
        return map;
    }

    public static final java.util.Map getMetas(DatabaseModel model, java.util.Map inheritedMeta,
                                               boolean onlyInheritable) {
        java.util.Map map = new HashMap();
        map.putAll(inheritedMeta);

//        Iterator iter = node.elementIterator("meta");
//        while (iter.hasNext()) {
//            Element metaNode = (Element) iter.next();
//            boolean inheritable = Boolean
//                    .valueOf(metaNode.attributeValue("inherit"))
//                    .booleanValue();
//            if (onlyInheritable & !inheritable) {
//                continue;
//            }
//            String name = metaNode.attributeValue("attribute");
//
//            MetaAttribute meta = (MetaAttribute) map.get(name);
//            MetaAttribute inheritedAttribute = (MetaAttribute) inheritedMeta.get(name);
//            if (meta == null) {
//                meta = new MetaAttribute(name);
//                map.put(name, meta);
//            } else if (meta == inheritedAttribute) { // overriding inherited meta attribute. HBX-621 & HBX-793
//                meta = new MetaAttribute(name);
//                map.put(name, meta);
//            }
//            meta.addValue(metaNode.getText());
//        }
        return map;
    }
//
//    public static String getEntityName(Element elem, Mappings model) {
//        String entityName = elem.attributeValue("entity-name");
//        return entityName == null ? getClassName(elem.attribute("class"), model) : entityName;
//    }

//    private static String getClassName(Attribute att, Mappings model) {
//        if (att == null) return null;
//        return getClassName(att.getValue(), model);
//    }

    public static String getClassName(String unqualifiedName, Mappings model) {
        return getClassName(unqualifiedName, model.getDefaultPackage());
    }

    public static String getClassName(String unqualifiedName, String defaultPackage) {
        if (unqualifiedName == null) return null;
        if (unqualifiedName.indexOf('.') < 0 && defaultPackage != null) {
            return defaultPackage + '.' + unqualifiedName;
        }
        return unqualifiedName;
    }

//    private static void parseFilterDef(Element element, Mappings mappings) {
//        String name = element.attributeValue("name");
//        LOG.debugf("Parsing filter-def [%s]", name);
//        String defaultCondition = element.getTextTrim();
//        if (StringHelper.isEmpty(defaultCondition)) {
//            defaultCondition = element.attributeValue("condition");
//        }
//        HashMap paramMappings = new HashMap();
//        Iterator params = element.elementIterator("filter-param");
//        while (params.hasNext()) {
//            final Element param = (Element) params.next();
//            final String paramName = param.attributeValue("name");
//            final String paramType = param.attributeValue("type");
//            LOG.debugf("Adding filter parameter : %s -> %s", paramName, paramType);
//            final Type heuristicType = mappings.getTypeResolver().heuristicType(paramType);
//            LOG.debugf("Parameter heuristic type : %s", heuristicType);
//            paramMappings.put(paramName, heuristicType);
//        }
//        LOG.debugf("Parsed filter-def [%s]", name);
//        FilterDefinition def = new FilterDefinition(name, defaultCondition, paramMappings);
//        mappings.addFilterDefinition(def);
//    }
//
//    private static void parseFilter(Element filterElement, Filterable filterable, Mappings model) {
//        final String name = filterElement.attributeValue("name");
//        String condition = filterElement.getTextTrim();
//        if (StringHelper.isEmpty(condition)) {
//            condition = filterElement.attributeValue("condition");
//        }
//        //HHTODO: bad implementation, cos it depends upon ordering of mapping doc
//        //      fixing this requires that Collection/PersistentClass gain access
//        //      to the Mappings reference from Configuration (or the filterDefinitions
//        //      map directly) sometime during Configuration.buildSessionFactory
//        //      (after all the types/filter-defs are known and before building
//        //      persisters).
//        if (StringHelper.isEmpty(condition)) {
//            condition = model.getFilterDefinition(name).getDefaultFilterCondition();
//        }
//        if (condition == null) {
//            throw new MappingException("no filter condition found for filter: " + name);
//        }
//        Iterator aliasesIterator = filterElement.elementIterator("aliases");
//        java.util.Map<String, String> aliasTables = new HashMap<String, String>();
//        while (aliasesIterator.hasNext()) {
//            Element alias = (Element) aliasesIterator.next();
//            aliasTables.put(alias.attributeValue("alias"), alias.attributeValue("table"));
//        }
//        LOG.debugf("Applying filter [%s] as [%s]", name, condition);
//        String autoAliasInjectionText = filterElement.attributeValue("autoAliasInjection");
//        boolean autoAliasInjection = StringHelper.isEmpty(autoAliasInjectionText) ? true : Boolean.parseBoolean(autoAliasInjectionText);
//        filterable.addFilter(name, condition, autoAliasInjection, aliasTables, null);
//    }
//
//    private static void parseFetchProfile(Element element, Mappings mappings, String containingEntityName) {
//        String profileName = element.attributeValue("name");
//        FetchProfile profile = mappings.findOrCreateFetchProfile(profileName, MetadataSource.HBM);
//        Iterator itr = element.elementIterator("fetch");
//        while (itr.hasNext()) {
//            final Element fetchElement = (Element) itr.next();
//            final String association = fetchElement.attributeValue("association");
//            final String style = fetchElement.attributeValue("style");
//            String entityName = fetchElement.attributeValue("entity");
//            if (entityName == null) {
//                entityName = containingEntityName;
//            }
//            if (entityName == null) {
//                throw new MappingException("could not determine entity for fetch-profile fetch [" + profileName + "]:[" + association + "]");
//            }
//            profile.addFetch(entityName, association, style);
//        }
//    }

    private static String getSubselect(TableMeta tableMeta) {
//FUTURE        String subselect = element.attributeValue("subselect");
//        if (subselect != null) {
//            return subselect;
//        } else {
//            Element subselectElement = element.element("subselect");
//            return subselectElement == null ? null : subselectElement.getText();
//        }
        return null;
    }

    private static String getSubselect(FieldMeta fieldMeta) {
//FUTURE        String subselect = element.attributeValue("subselect");
//        if (subselect != null) {
//            return subselect;
//        } else {
//            Element subselectElement = element.element("subselect");
//            return subselectElement == null ? null : subselectElement.getText();
//        }
        return null;
    }

    //    /**
//     * For the given document, locate all extends attributes which refer to
//     * entities (entity-name or class-name) not defined within said document.
//     *
//     * @param metadataXml The document to check
//     * @param mappings    The already processed mappings.
//     * @return The list of unresolved extends names.
//     */
//    public static java.util.List<String> getExtendsNeeded(XmlDocument metadataXml, Mappings mappings) {
//        java.util.List<String> extendz = new ArrayList<String>();
//        Iterator[] subclasses = new Iterator[3];
//        final Element hmNode = metadataXml.getDocumentTree().getRootElement();
//
//        Attribute packNode = hmNode.attribute("package");
//        final String packageName = packNode == null ? null : packNode.getValue();
//        if (packageName != null) {
//            mappings.setDefaultPackage(packageName);
//        }
//
//        // first, iterate over all elements capable of defining an extends attribute
//        // collecting all found extends references if they cannot be resolved
//        // against the already processed mappings.
//        subclasses[0] = hmNode.elementIterator("subclass");
//        subclasses[1] = hmNode.elementIterator("joined-subclass");
//        subclasses[2] = hmNode.elementIterator("union-subclass");
//
//        Iterator iterator = new JoinedIterator(subclasses);
//        while (iterator.hasNext()) {
//            final Element element = (Element) iterator.next();
//            final String extendsName = element.attributeValue("extends");
//            // mappings might contain either the "raw" extends name (in the case of
//            // an entity-name mapping) or a FQN (in the case of a POJO mapping).
//            if (mappings.getClass(extendsName) == null && mappings.getClass(getClassName(extendsName, mappings)) == null) {
//                extendz.add(extendsName);
//            }
//        }
//
//        if (!extendz.isEmpty()) {
//            // we found some extends attributes referencing entities which were
//            // not already processed.  here we need to locate all entity-names
//            // and class-names contained in this document itself, making sure
//            // that these get removed from the extendz list such that only
//            // extends names which require us to delay processing (i.e.
//            // external to this document and not yet processed) are contained
//            // in the returned result
//            final java.util.Set<String> set = new HashSet<String>(extendz);
//            EntityElementHandler handler = new EntityElementHandler() {
//                public void handleEntity(String entityName, String className, Mappings mappings) {
//                    if (entityName != null) {
//                        set.remove(entityName);
//                    } else {
//                        String fqn = getClassName(className, packageName);
//                        set.remove(fqn);
//                        if (packageName != null) {
//                            set.remove(StringHelper.unqualify(fqn));
//                        }
//                    }
//                }
//            };
//            recognizeEntities(mappings, hmNode, handler);
//            extendz.clear();
//            extendz.addAll(set);
//        }
//
//        return extendz;
//    }
//
//    /**
//     * Given an entity-containing-element (startNode) recursively locate all
//     * entity names defined within that element.
//     *
//     * @param mappings  The already processed mappings
//     * @param startNode The containing element
//     * @param handler   The thing that knows what to do whenever we recognize an
//     *                  entity-name
//     */
//    private static void recognizeEntities(
//            Mappings mappings,
//            final Element startNode,
//            EntityElementHandler handler) {
//        Iterator[] classes = new Iterator[4];
//        classes[0] = startNode.elementIterator("class");
//        classes[1] = startNode.elementIterator("subclass");
//        classes[2] = startNode.elementIterator("joined-subclass");
//        classes[3] = startNode.elementIterator("union-subclass");
//
//        Iterator classIterator = new JoinedIterator(classes);
//        while (classIterator.hasNext()) {
//            Element element = (Element) classIterator.next();
//            handler.handleEntity(
//                    element.attributeValue("entity-name"),
//                    element.attributeValue("name"),
//                    mappings
//            );
//            recognizeEntities(mappings, element, handler);
//        }
//    }
//
//    private static interface EntityElementHandler {
//        public void handleEntity(String entityName, String className, Mappings mappings);
//    }
//
    private static class ResolveUserTypeMappingSecondPass implements SecondPass {

        private SimpleValue simpleValue;
        private String typeName;
        private Mappings mappings;
        private Properties parameters;

        public ResolveUserTypeMappingSecondPass(SimpleValue simpleValue,
                                                String typeName, Mappings mappings, Properties parameters) {
            this.simpleValue = simpleValue;
            this.typeName = typeName;
            this.parameters = parameters;
            this.mappings = mappings;
        }

        @Override
        public void doSecondPass(java.util.Map persistentClasses) {
            resolveAndBindTypeDef(simpleValue, mappings, typeName, parameters);
        }

    }
}
