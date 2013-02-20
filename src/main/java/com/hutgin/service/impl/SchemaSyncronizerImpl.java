package com.hutgin.service.impl;

import com.hutgin.dao.Dao;
import com.hutgin.dao.Executor;
import com.hutgin.dao.MetaDataDao;
import com.hutgin.service.SchemaSyncronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaSyncronizerImpl implements SchemaSyncronizer {

    @Autowired
    private MetaDataDao metaDataDao;

    @Autowired
    private Executor executor;

    @Autowired
    private Dao dao;

    @Override
    public void updateDB() {
        // TODO list all tables using table-specific dao (hibernate?) with columns, etc
//        List<Table> metaTables = dao.findAll()

    }
}
