package com.hutgin.service.impl;

import com.hutgin.dao.Dao;
import com.hutgin.dao.Executor;
import com.hutgin.dao.MetaDataDao;
import com.hutgin.entity.Table;
import com.hutgin.service.SchemaSyncronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Table> metaTables = dao.findAll(Table.getDescriptor());

    }
}
