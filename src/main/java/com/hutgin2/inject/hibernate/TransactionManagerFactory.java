package com.hutgin2.inject.hibernate;

import org.springframework.transaction.PlatformTransactionManager;

public interface TransactionManagerFactory {

    PlatformTransactionManager getTransactionManager();

    void refresh();
}
