package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;
import com.hutgin2.inject.hibernate.TransactionManagerFactory;
import com.hutgin2.service.GeneralEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;

@Service("generalEntityTransactionalService")
public class GeneralEntityTransactionalServiceImpl implements GeneralEntityService {

    @Autowired
    private GeneralEntityService generalEntityService;

    @Autowired
    private TransactionManagerFactory transactionManagerFactory;

    @Autowired
    private DefaultTransactionDefinition defaultTransactionDefinition;

    private TransactionTemplate transaction() {
        return new TransactionTemplate(transactionManagerFactory.getTransactionManager(), defaultTransactionDefinition);
    }

    private TransactionTemplate readonly() {
        TransactionTemplate tr = new TransactionTemplate(transactionManagerFactory.getTransactionManager(), defaultTransactionDefinition);
        tr.setReadOnly(true);
        return tr;
    }


    @Override
    public Entity find(final TableMeta type, final Serializable id) {
        return readonly().execute(new TransactionCallback<Entity>() {
            @Override
            public Entity doInTransaction(TransactionStatus status) {
                return generalEntityService.find(type, id);
            }
        });
    }

    @Override
    public boolean save(final Entity entity) {
        return transaction().execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                return generalEntityService.save(entity);
            }
        });
    }

    @Override
    public boolean remove(final Entity entity) {
        return transaction().execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                return generalEntityService.remove(entity);
            }
        });
    }

    @Override
    public EntityCollection findAll(final TableMeta type) {
        return readonly().execute(new TransactionCallback<EntityCollection>() {
            @Override
            public EntityCollection doInTransaction(TransactionStatus status) {
                return generalEntityService.findAll(type);
            }
        });
    }
}
