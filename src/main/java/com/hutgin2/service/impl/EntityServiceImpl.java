package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.EntityDao;
import com.hutgin2.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private EntityDao entityDao;

    @Override
    @Transactional(readOnly = true)
    public List getAll(TableMeta tableMeta) {
        return entityDao.getAll(tableMeta);
    }
}
