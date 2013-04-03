package com.hutgin2.inject.hibernate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

//@Component

/**
 * Cannot be used because depends on other beans but context already started
 */
@Deprecated
public class SpringMetaContextBuilder implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @Autowired
    private EntitySessionFactory entitySessionFactory;

    @Autowired
    private TransactionManagerFactory transactionManagerFactory;

    public void refreshContext() {
//        if (context.isRunning())
//            context.stop();
        context.refresh();
        SingletonBeanRegistry registry = context.getBeanFactory();
        registry.registerSingleton("sessionFactoryMain", entitySessionFactory.getSessionFactory());
        registry.registerSingleton("transactionManagerMain", transactionManagerFactory.getTransactionManager());
        context.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
        refreshContext();
    }
}
