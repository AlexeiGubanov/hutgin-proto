package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class EntitySessionFactoryTest {

    @Autowired
    private EntitySessionFactory sessionFactory;

    @Test
    public void testGetSessionFactory1() throws Exception {
        assertFalse(sessionFactory == null);


        DatabaseModel model = new DatabaseModel();
        List<TableMeta> tables = new ArrayList<>();

        TableMeta t1 = new TableMeta("Employee");

        FieldMeta id = new FieldMeta();
        id.setName("id");
        id.setType(String.class);
        id.setTableName(t1.getName());
        t1.getFields().add(id);

        ConstraintPKMeta pk = new ConstraintPKMeta();
        pk.setType(ConstraintType.PK);
        pk.getFields().add(id);
        pk.setName("PK");
        pk.setTable(t1);
        pk.setTableName(t1.getName());
        t1.getConstraints().add(pk);

        FieldMeta f1 = new FieldMeta();
        f1.setName("firstname");
        f1.setType(String.class);
        f1.setTableName(t1.getName());
        t1.getFields().add(f1);

        tables.add(t1);
        model.setTables(tables);
        sessionFactory.init(model);

        Session s = sessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("firstname", "123");
        s.save("Employee", map);
        DetachedCriteria dc = DetachedCriteria.forEntityName("Employee");
        Criteria c = dc.getExecutableCriteria(s);
        assertEquals(1, c.list().size());
        tx.commit();
    }

    //    @Test
    public void testGetSessionFactory() throws Exception {
        assertFalse(sessionFactory == null);


        DatabaseModel model = new DatabaseModel();
        List<TableMeta> tables = new ArrayList<>();

        TableMeta t1 = new TableMeta("Employee");

        FieldMeta id = new FieldMeta();
        id.setName("id");
        id.setPrimaryKey(true);
        id.setType(String.class);
        id.setTableName(t1.getName());
        t1.getFields().add(id);

        FieldMeta f1 = new FieldMeta();
        f1.setName("firstname");
        f1.setType(String.class);
        f1.setTableName(t1.getName());
        t1.getFields().add(f1);

        tables.add(t1);
        model.setTables(tables);

        sessionFactory.initWithDatabaseModelSourceProcessor(model);

        Session s = sessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("firstname", "123");
        s.save("Employee", map);
        DetachedCriteria dc = DetachedCriteria.forEntityName("Employee");
        Criteria c = dc.getExecutableCriteria(s);
        assertEquals(1, c.list().size());
        tx.commit();
    }

    //    @Test
    public void testGetSessionFactory2() throws Exception {
        assertFalse(sessionFactory == null);
        sessionFactory.initAsMetamodel("testData/hbm/jaxb-sample.hbm.xml");
        Session s = sessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("firstname", "123");
        s.save("Employee", map);
        DetachedCriteria dc = DetachedCriteria.forEntityName("Employee");
        Criteria c = dc.getExecutableCriteria(s);
        assertEquals(1, c.list().size());
        tx.commit();
    }
}
