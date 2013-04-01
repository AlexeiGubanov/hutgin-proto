package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.EntityDao;
import com.hutgin2.inject.hibernate.TransactionManagerFactory;
import com.hutgin2.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
//@Transactional(value = "main")
public class EntityServiceImpl implements EntityService {

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private TransactionManagerFactory transactionManagerFactory;

    @Autowired
    private DefaultTransactionDefinition defaultTransactionDefinition;

    private TransactionTemplate localCopy() {
        return new TransactionTemplate(transactionManagerFactory.getTransactionManager(), defaultTransactionDefinition);
    }

    @Override
//    @Transactional(readOnly = true)
    public List getAll(final TableMeta tableMeta) {
        TransactionTemplate tr = localCopy();
        tr.setReadOnly(true);

        return (List) tr.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                return entityDao.getAll(tableMeta);
            }
        });
    }
}
