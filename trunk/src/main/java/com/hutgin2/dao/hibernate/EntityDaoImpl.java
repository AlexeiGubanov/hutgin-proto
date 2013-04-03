package com.hutgin2.dao.hibernate;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.EntityDao;
import com.hutgin2.inject.hibernate.EntitySessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Deprecated
public class EntityDaoImpl implements EntityDao {

    @Autowired
    private EntitySessionFactory entitySessionFactory;
//
//    @Autowired()
//    @Qualifier("sessionFactoryMain")
//    private SessionFactory sessionFactory;


    @Override
    public List getAll(TableMeta tableMeta) {
        return new EntityWrappedDao(entitySessionFactory.getSessionFactory(), tableMeta).getAll();
    }

    @Override
    public void save(TableMeta tableMeta, Map<String, Object> value) {
        new EntityWrappedDao(entitySessionFactory.getSessionFactory(), tableMeta).save(value);
    }
}
