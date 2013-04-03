package com.hutgin2.dao.hibernate;

import org.hibernate.SessionFactory;

import java.util.Map;

public class MapEntityNameDAOImpl extends GenericEntityNameDAOImpl<Map<String, Object>> {

    public MapEntityNameDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
