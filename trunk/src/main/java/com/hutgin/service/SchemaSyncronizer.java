package com.hutgin.service;

public interface SchemaSyncronizer {

    /**
     * Syncronize database structure with metadata provided in according TableMeta entity. <br/>
     * Executes only missing elements, if you want to remove some entities - use direct scripts.
     */
    void updateDB();

    /**
     * Syncronize metadata (TableMeta entity) with database schema
     */
//    void updateMeta();


}
