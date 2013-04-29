package com.hutgin2.core.service.impl;

import com.hutgin2.core.meta.DatabaseModel;
import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.MetaModel;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaModelImpl implements MetaModel {

    private DatabaseModel cache;

    @Autowired
    protected TableMetaService tableMetaService;


    @Override
    public synchronized DatabaseModel getModel() {
        if (cache == null)
            refresh();
        return cache;
    }

    @Override
    public synchronized void refresh() {
        List<TableMeta> tables = tableMetaService.findAll();
        cache = new DatabaseModel();
        cache.setTables(tables);
    }
}
