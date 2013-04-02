package com.hutgin2.dao.hibernate;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.GeneralMapDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Map;

public class GeneralMapDAOImpl implements GeneralMapDAO {

    private TableMeta tableMeta;
    private SessionFactory sessionFactory;

    public GeneralMapDAOImpl(TableMeta tableMeta, SessionFactory sessionFactory) {
        this.tableMeta = tableMeta;
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public Map<String, Object> find(Serializable id) {
        return (Map<String, Object>) getSession().get(tableMeta.getName(), id);
    }
}
