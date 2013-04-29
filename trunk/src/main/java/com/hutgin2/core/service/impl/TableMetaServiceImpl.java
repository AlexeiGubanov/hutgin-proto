package com.hutgin2.core.service.impl;

import com.hutgin2.core.dao.TableDao;
import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TableMetaServiceImpl extends GenericServiceImpl<TableMeta, String> implements TableMetaService {

    @Autowired
    public TableMetaServiceImpl(TableDao tableDao) {
        super(tableDao);
    }

}
