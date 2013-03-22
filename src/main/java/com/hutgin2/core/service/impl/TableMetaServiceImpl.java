package com.hutgin2.core.service.impl;

import com.hutgin2.core.dao.TableDao;
import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TableMetaServiceImpl implements TableMetaService {

    @Autowired
    private TableDao tableDao;

    @Override
    @Transactional(readOnly = true)
    public List<TableMeta> getAll() {
        return tableDao.findAll();
    }

    @Override
    @Transactional
    public void save(TableMeta tableMeta) {
        tableDao.save(tableMeta);
    }
}
