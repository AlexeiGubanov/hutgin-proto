package com.hutgin.dbsupport.database;

public interface Database {
    String escapeName(String name);

    String escapeTableName(String schema, String table);

    boolean isSupportShemas();

    String getDefaultSchema();

    void setDefaultSchema(String schema);

    String escapeColumnName(String name);
}
