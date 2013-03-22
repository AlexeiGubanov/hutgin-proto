package com.hutgin2.inject.hibernate;

import com.hutgin2.core.meta.DatabaseModel;
import org.hibernate.SessionFactory;

public interface EntitySessionFactory {
    SessionFactory getSessionFactory();

    void init(DatabaseModel model);
}
