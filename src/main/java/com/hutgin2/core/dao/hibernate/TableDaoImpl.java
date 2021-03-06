package com.hutgin2.core.dao.hibernate;

import com.hutgin2.core.dao.TableDao;
import com.hutgin2.core.meta.TableMeta;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TableDaoImpl extends GenericDAOImpl<TableMeta, String> implements TableDao {

    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactoryMeta) {
        super.setSessionFactory(sessionFactoryMeta);
    }
}
