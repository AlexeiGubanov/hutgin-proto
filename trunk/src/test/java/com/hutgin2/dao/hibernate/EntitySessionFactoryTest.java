package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
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
    public void testGetSessionFactory() throws Exception {
        assertFalse(sessionFactory == null);


        DatabaseModel model = new DatabaseModel();
        List<TableMeta> tables = new ArrayList<>();

        TableMeta t1 = new TableMeta("Employee");
        FieldMeta f1 = new FieldMeta();
        f1.setName("firstname");
        f1.setType(String.class);
        t1.getFields().add(f1);

        tables.add(t1);
        model.setTables(tables);
        sessionFactory.init(model);

        Session s = sessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        Map<String, Object> map = new HashMap<>();
        map.put("firstname", "123");
        s.save("Employee", map);
        DetachedCriteria dc = DetachedCriteria.forEntityName("Employee");
        Criteria c = dc.getExecutableCriteria(s);
        assertEquals(1, c.list().size());
        tx.commit();
    }
}
