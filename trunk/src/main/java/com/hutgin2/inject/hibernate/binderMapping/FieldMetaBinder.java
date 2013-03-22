package com.hutgin2.inject.hibernate.binderMapping;

import com.hutgin2.core.meta.ConstraintIDXMeta;
import com.hutgin2.core.meta.ConstraintUQMeta;
import com.hutgin2.core.meta.FieldMeta;
import com.hutgin2.core.meta.ValueGenerationStrategy;
import com.hutgin2.inject.hibernate.binder.SingularAttributeSourceImpl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.Mappings;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.cfg.SecondPass;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.mapping.*;
import org.hibernate.type.BasicType;

import java.util.Properties;

public class FieldMetaBinder implements Binder {

    private FieldMeta fieldMeta;
    private Table table;
    private RootClass persistentClass;

    public FieldMetaBinder(FieldMeta fieldMeta, Table table, RootClass persistentClass) {
        this.fieldMeta = fieldMeta;
        this.table = table;
        this.persistentClass = persistentClass;
    }


    private void bindSimpleProperty(Mappings mappings) {
        SingularAttributeSourceImpl attributeSource = new SingularAttributeSourceImpl(fieldMeta);
        boolean isNullable = !fieldMeta.isRequired();

        // SIMPLE VALUE
        SimpleValue value = new SimpleValue(mappings, table);

        // PROPERTY
        Property property = new Property();
        property.setValue(value);
        property.setName(fieldMeta.getName());
        property.setNodeName(fieldMeta.getName());

        if (fieldMeta.isPrimaryKey()) {
            isNullable = false;
            persistentClass.setIdentifier(value);
            persistentClass.setIdentifierProperty(property);
            persistentClass.setDeclaredIdentifierProperty(property);
            bindIdGeneration(value, mappings);
            table.setIdentifierValue(value);
        }
        String propertyAccessorName = attributeSource.getPropertyAccessorName();
        if (propertyAccessorName != null) {
            property.setPropertyAccessorName(propertyAccessorName);
        } else {
            property.setPropertyAccessorName(mappings.getDefaultAccess());
        }
        //        Attribute cascadeNode = null; // TODO check designation node.attribute("cascade");
        //        property.setCascade(cascadeNode == null ? mappings.getDefaultCascade() : cascadeNode
        //                .getValue());
        property.setInsertable(attributeSource.isInsertable());
        property.setUpdateable(attributeSource.isUpdatable());
        property.setOptimisticLocked(attributeSource.isIncludedInOptimisticLocking());
        property.setGeneration(attributeSource.getGeneration());
        property.setLazy(attributeSource.isLazy());
        if (!fieldMeta.isPrimaryKey()) {
            persistentClass.addProperty(property);
        }

        // TYPE
        String typeName = fieldMeta.getType().getName();
        Properties parameters = new Properties(); // TODO check if field type requires additional params
        resolveAndBindTypeDef(value, mappings, typeName, parameters);
        if (persistentClass.hasPojoRepresentation()) {
            value.setTypeUsingReflection(persistentClass.getClassName(), fieldMeta.getName());
        }


//        String formulaNode = null; //TODO check support
//        if (formulaNode != null) {
//            Formula f = new Formula();
//            f.setFormula(formulaNode);
//            value.addFormula(f);
//        } else {
        // COLUMN
        Column column = new Column();
        column.setValue(value);

        // LENGTH AND PRECISION
        Integer lengthNode = fieldMeta.getSize();
        // TODO regarding dataType (for numbers - scale, for string - length
        if (lengthNode != null) {
            column.setLength(lengthNode);
            column.setScale(lengthNode);
        }
        Integer precNode = fieldMeta.getPrecision();
        if (precNode != null) column.setPrecision(precNode);

        // REQUIRED
        column.setNullable(isNullable);

        //UNIQ TODO check if this uniq is already presented in constraint and proceed as Unique constraint
        column.setUnique(fieldMeta.isUniq());

        //        column.setCheckConstraint(node.attributeValue("check")); //TODO check designation

        //DEFAULT VALUE
        if (fieldMeta.getInsertGenerationStrategy() == ValueGenerationStrategy.DEFAULT_VALUE) {
            column.setDefaultValue(fieldMeta.getInsertDefaultValue());
        }

        //        Attribute typeNode = node.attribute("sql-type"); // TODO check support
        //        if (typeNode != null) column.setSqlType(typeNode.getValue());

        //        String customWrite = node.attributeValue("write");  //TODO check designation
        //        if (customWrite != null && !customWrite.matches("[^?]*\\?[^?]*")) {
        //            throw new MappingException("write expression must contain exactly one value placeholder ('?') character");
        //        }
        //        column.setCustomWrite(customWrite);
        //        column.setCustomRead(node.attributeValue("read")); //TODO check designation

        // COMMENT
        column.setComment(fieldMeta.getDescription());

        // COLUMN NAME
        String columnName = StringUtils.isNotEmpty(fieldMeta.getFieldName()) ? fieldMeta.getFieldName() : fieldMeta.getName();
        String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(
                columnName, fieldMeta.getName()
        );
        column.setName(mappings.getNamingStrategy().columnName(columnName));

        table.addColumn(column);
        mappings.addColumnBinding(logicalColumnName, column, table);
        value.addColumn(column);


//            bindIndex(fieldMeta, table, column, mappings);  //TODO review!!
//            bindUniqueKey(fieldMeta, table, column, mappings); //TODO review!!

//                        if (value.getColumnSpan() == 0) {        // TODO review, check designation
//                            Column column2 = new Column();
//                            column.setValue(simpleValue);
//                            bindColumn(fieldMeta, column, isNullable);
//                            column.setName(mappings.getNamingStrategy().propertyToColumnName(propertyPath));
//                            String logicalName = mappings.getNamingStrategy().logicalColumnName(null, propertyPath);
//                            mappings.addColumnBinding(logicalName, column, table);
//                            simpleValue.getTable().addColumn(column);
//                            simpleValue.addColumn(column);
//                            bindIndex(fieldMeta, table, column, mappings);
//                            bindUniqueKey(fieldMeta, table, column, mappings);
//                        }
//        }


//TODO REVIEW                    // this is done here 'cos we might only know the type here (ugly!)
//                    // HH TODO: improve this a lot:
//                    if (value instanceof ToOne) {
//                        ToOne toOne = (ToOne) value;
//                        String propertyRef = toOne.getReferencedPropertyName();
//                        if (propertyRef != null) {
//                            mappings.addUniquePropertyReference(toOne.getReferencedEntityName(), propertyRef);
//                        }
//                    } else if (value instanceof org.hibernate.mapping.Collection) {
//                        org.hibernate.mapping.Collection coll = (org.hibernate.mapping.Collection) value;
//                        String propertyRef = coll.getReferencedPropertyName();
//                        // not necessarily a *unique* property reference
//                        if (propertyRef != null) {
//                            mappings.addPropertyReference(coll.getOwnerEntityName(), propertyRef);
//                        }
//                    }
//                    value.createForeignKey();
    }

    private void resolveAndBindTypeDef(SimpleValue simpleValue, Mappings mappings, String typeName, Properties parameters) {
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
            // TODO review regarding secondPass
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

    private void bindIdGeneration(SimpleValue value, Mappings mappings) {

        switch (fieldMeta.getInsertGenerationStrategy()) {
            case DEFAULT_VALUE:
                throw new NotYetImplementedException("ID generation by default value is not yet implemented");
            case CLASS:
                String generatorName = fieldMeta.getInsertGenerator();
                // FIXME currently generatorName is hibernate generator strategy name!
                value.setIdentifierGeneratorStrategy(generatorName);
                Properties params = new Properties();
                // YUCK!  but cannot think of a clean way to do this given the string-config based scheme
                params.put(PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER, mappings.getObjectNameNormalizer());

                if (mappings.getSchemaName() != null) {
                    params.setProperty(
                            PersistentIdentifierGenerator.SCHEMA,
                            mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(mappings.getSchemaName())
                    );
                }
                if (mappings.getCatalogName() != null) {
                    params.setProperty(
                            PersistentIdentifierGenerator.CATALOG,
                            mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(mappings.getCatalogName())
                    );
                }
// TODO check support additional params
//                Iterator iter = subnode.elementIterator("param");
//                while (iter.hasNext()) {
//                    Element childNode = (Element) iter.next();
//                    params.setProperty(childNode.attributeValue("name"), childNode.getTextTrim());
//                }
//
                value.setIdentifierGeneratorProperties(params);
                break;
            case STORED_PROCEDURE:
                throw new NotYetImplementedException("ID generation by stored procedure value is not yet implemented");
        }


        // ID UNSAVED-VALUE // TODO check support
//        Attribute nullValueNode = node.attribute("unsaved-value");
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

    /**
     * TODO may be bind all indexes within one call, than several calls to this method according to fieldMeta
     */
    private static void bindIndex(FieldMeta fieldMeta, Table table, Column column, Mappings mappings) {
        if (table == null)
            return;
        for (ConstraintIDXMeta constraintMeta : fieldMeta.getTable().getConstraintIDXs()) {
            if (constraintMeta.getFields().contains(fieldMeta)) {
                table.getOrCreateIndex(constraintMeta.getName()).addColumn(column);
            }
            // TODO what about  ConstraintIDXMeta.uniq?
            // TODO where composite index should be created?  see Index.addColumns
            // TODO how index name is generated? may be it requires sync with constraintMeta.name?
        }
    }

    /**
     * TODO may be bind all uqs within one call, than several calls to this method according to fieldMeta
     */

    private static void bindUniqueKey(FieldMeta fieldMeta, Table table, Column column, Mappings mappings) {
        if (table == null)
            return;
        for (ConstraintUQMeta constraintMeta : fieldMeta.getTable().getConstraintUQs()) {
            if (constraintMeta.getFields().contains(fieldMeta)) {
                table.getOrCreateUniqueKey(constraintMeta.getName()).addColumn(column);
            }
            // TODO where composite index should be created? see UniqueKey.addColumns
            // TODO how index name is generated? may be it requires sync with constraintMeta.name?
        }
    }


    @Override
    public void bind(Mappings mappings) {
        // TODO add collections and associations support
        switch (fieldMeta.getAssociationType()) {
            case NONE:
                // TODO add component support
                bindSimpleProperty(mappings);
                break;
            default:
                throw new NotYetImplementedException("Collections not yet supported");
        }
    }

    public class ResolveUserTypeMappingSecondPass implements SecondPass {

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
