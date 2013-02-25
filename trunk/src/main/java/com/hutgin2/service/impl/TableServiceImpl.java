package com.hutgin2.service.impl;

import com.hutgin2.dao.TableDao;
import com.hutgin2.meta.TableMeta;
import com.hutgin2.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableDao tableDao;

    @Override
    @Transactional(readOnly = true)
    public List<TableMeta> getAll() {
        return tableDao.getAll();
    }
}
