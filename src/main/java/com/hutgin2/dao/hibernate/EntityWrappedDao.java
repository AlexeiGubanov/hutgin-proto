package com.hutgin2.dao.hibernate;

import com.hutgin2.core.meta.TableMeta;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;
import java.util.Map;

public class EntityWrappedDao {

    private SessionFactory sessionFactory;
    private TableMeta tableMeta;

    public EntityWrappedDao(SessionFactory sessionFactory, TableMeta tableMeta) {
        this.sessionFactory = sessionFactory;
        this.tableMeta = tableMeta;
    }

    public List getAll() {
        DetachedCriteria dc = DetachedCriteria.forEntityName(tableMeta.getName());
        Criteria c = dc.getExecutableCriteria(sessionFactory.getCurrentSession());
        return c.list();
    }


    public void save(Map<String, Object> value) {
        sessionFactory.getCurrentSession().save(tableMeta.getName(), value);
    }
}
