package com.hutgin2.dao.hibernate;

import com.hutgin2.dao.TableDao;
import com.hutgin2.meta.TableMeta;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableDaoImpl implements TableDao {

    @Autowired
    protected SessionFactory sessionFactoryMeta;

    @Override
    public List<TableMeta> getAll() {
        DetachedCriteria dc = DetachedCriteria.forClass(TableMeta.class);
        Criteria c = dc.getExecutableCriteria(sessionFactoryMeta.getCurrentSession());
        return c.list();
    }

    @Override
    public void persist(TableMeta tableMeta) {
        sessionFactoryMeta.getCurrentSession().persist(tableMeta);
    }
}
