package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.GeneralEntityDAO;
import com.hutgin2.entity.Entity;
import com.hutgin2.inject.hibernate.TransactionManagerFactory;
import com.hutgin2.service.GeneralEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;

@Service
public class GeneralEntityServiceImpl implements GeneralEntityService {

    @Autowired
    private GeneralEntityDAO entityDao;

    @Autowired
    private TransactionManagerFactory transactionManagerFactory;

    @Autowired
    private DefaultTransactionDefinition defaultTransactionDefinition;

    private TransactionTemplate localCopy() {
        return new TransactionTemplate(transactionManagerFactory.getTransactionManager(), defaultTransactionDefinition);
    }


    @Override
    public Entity find(final TableMeta type, final Serializable id) {
        TransactionTemplate tr = localCopy();
        tr.setReadOnly(true);

        return (Entity) tr.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                return entityDao.find(type, id);
            }
        });
    }
}
