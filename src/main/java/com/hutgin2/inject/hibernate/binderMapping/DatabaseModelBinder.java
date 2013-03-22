package com.hutgin2.inject.hibernate.binderMapping;

import com.hutgin2.core.meta.DatabaseModel;
import com.hutgin2.core.meta.TableMeta;
import org.hibernate.cfg.Mappings;

public class DatabaseModelBinder implements Binder {

    private DatabaseModel databaseModel;

    public DatabaseModelBinder(DatabaseModel databaseModel) {
        this.databaseModel = databaseModel;
    }


    @Override
    public void bind(Mappings mappings) {
        extractRootAttrubutes();
        for (TableMeta tableMeta : databaseModel.getTables()) {
            new TableMetaBinder(tableMeta).bind(mappings);
        }
    }

    private void extractRootAttrubutes() {
//        TODO extract from model
//        Attribute schemaNode = hmNode.attribute( "schema" );
//        mappings.setSchemaName( ( schemaNode == null ) ? null : schemaNode.getValue() );
//
//        Attribute catalogNode = hmNode.attribute( "catalog" );
//        mappings.setCatalogName( ( catalogNode == null ) ? null : catalogNode.getValue() );
//
//        Attribute dcNode = hmNode.attribute( "default-cascade" );
//        mappings.setDefaultCascade( ( dcNode == null ) ? "none" : dcNode.getValue() );
//
//        Attribute daNode = hmNode.attribute( "default-access" );
//        mappings.setDefaultAccess( ( daNode == null ) ? "property" : daNode.getValue() );
//
//        Attribute dlNode = hmNode.attribute( "default-lazy" );
//        mappings.setDefaultLazy( dlNode == null || dlNode.getValue().equals( "true" ) );
//
//        Attribute aiNode = hmNode.attribute( "auto-import" );
//        mappings.setAutoImport( ( aiNode == null ) || "true".equals( aiNode.getValue() ) );
//
//        Attribute packNode = hmNode.attribute( "package" );
//        if ( packNode != null ) mappings.setDefaultPackage( packNode.getValue() );
    }
}
