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
    protected SessionFactory sessionFactory;

    @Override
    public List<TableMeta> getAll() {
        DetachedCriteria dc = DetachedCriteria.forClass(TableMeta.class);
        Criteria c = dc.getExecutableCriteria(sessionFactory.getCurrentSession());
        return c.list();
    }
}
