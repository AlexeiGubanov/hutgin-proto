package com.hutgin2.core.dao.hibernate;

import com.hutgin2.core.dao.TableDao;
import com.hutgin2.core.meta.TableMeta;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableDaoImpl implements TableDao { //extends GenericDAOImpl<TableMeta,String>

//    @Override
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactoryMeta) {
//        super.setSessionFactory(sessionFactoryMeta);
//    }


    @Autowired
    protected SessionFactory sessionFactoryMeta;

    @Override
    public List<TableMeta> findAll() {
        DetachedCriteria dc = DetachedCriteria.forClass(TableMeta.class);
        Criteria c = dc.getExecutableCriteria(sessionFactoryMeta.getCurrentSession());
        return c.list();
    }

    @Override
    public void save(TableMeta tableMeta) {
        sessionFactoryMeta.getCurrentSession().persist(tableMeta);
    }
}
