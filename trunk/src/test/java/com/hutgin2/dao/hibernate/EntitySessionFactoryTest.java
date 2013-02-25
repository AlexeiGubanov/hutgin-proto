package com.hutgin2.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class EntitySessionFactoryTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testGetSessionFactory() throws Exception {
        assertFalse(sessionFactory == null);


//        HibernateTransactionManager tm = new HibernateTransactionManager(sessionFactory);

        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        Map<String, Object> map = new HashMap<>();
        map.put("firstname", "123");
        s.persist("Employee", map);


        DetachedCriteria dc = DetachedCriteria.forEntityName("Employee");
        Criteria c = dc.getExecutableCriteria(s);
        assertEquals(1, c.list().size());
        tx.commit();

    }
}
