package com.hutgin2.dao.jpa;

import com.hutgin2.dao.TableDao;
import com.hutgin2.meta.TableMeta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

//@Repository("tableDaoJpa")
public class TableDaoImpl implements TableDao {

    private EntityManager entityManager;

    //    @Autowired
//    @Qualifier(value = "entityManagerFactoryMeta")
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }


    @Override
    public List<TableMeta> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TableMeta> c = cb.createQuery(TableMeta.class);
        Root<TableMeta> root = c.from(TableMeta.class);
        TypedQuery<TableMeta> q = entityManager.createQuery(c);
        return q.getResultList();
    }
}
