package com.hutgin.dbsupport.database;

import org.apache.commons.lang3.StringUtils;

public class AbstractDatabase implements Database {

    private String defaultSchema;


    @Override
    public String escapeName(String name) {
        return name;
    }

    @Override
    public String escapeTableName(String schema, String table) {
        String aSchema = StringUtils.isNotEmpty(schema) ? schema : getDefaultSchema();
        if (!isSupportShemas() || StringUtils.isEmpty(aSchema)) {
            return escapeName(table);
        } else {
            return escapeName(aSchema) + "." + escapeName(table);
        }
    }

    @Override
    public boolean isSupportShemas() {
        return (StringUtils.isNotEmpty(defaultSchema));
    }

    @Override
    public String getDefaultSchema() {
        return defaultSchema;
    }

    @Override
    public void setDefaultSchema(String schema) {
        this.defaultSchema = schema;
    }

    @Override
    public String escapeColumnName(String name) {
        return escapeName(name);
    }
}
