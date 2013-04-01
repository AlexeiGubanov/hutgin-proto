package com.hutgin2.inject.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class TransactionManagerFactoryImpl implements TransactionManagerFactory {

    @Autowired
    private EntitySessionFactory entitySessionFactory;

    private PlatformTransactionManager transactionManager;

    @Override
    public PlatformTransactionManager getTransactionManager() {
        if (transactionManager == null) {
            refresh();
        }
        return transactionManager;
    }

    @Override
    public void refresh() {
        SessionFactory sessionFactory = entitySessionFactory.getSessionFactory();
        transactionManager = new HibernateTransactionManager(sessionFactory);
    }
}
