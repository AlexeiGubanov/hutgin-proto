package com.hutgin2.dao.hibernate;

import com.hutgin2.meta.DatabaseModel;
import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
import com.hutgin2.meta.ValueGenerationStrategy;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class EntitySessionFactoryTest {

    @Autowired
    private EntitySessionFactory sessionFactory;

    @Test
    public void testGetSessionFactoryWithDMSourceProcessor() throws Exception {
        assertFalse(sessionFactory == null);


        DatabaseModel model = new DatabaseModel();
        List<TableMeta> tables = new ArrayList<>();

        TableMeta t1 = new TableMeta("Employee");

        FieldMeta id = new FieldMeta();
        id.setName("id");
        id.setInsertGenerationStrategy(ValueGenerationStrategy.CLASS);
        id.setInsertGenerator("org.hibernate.id.IncrementGenerator");
        id.setPrimaryKey(true);
        id.setType(Long.class);
        id.setTable(t1);
        id.setTableName(t1.getName());
        t1.getFields().add(id);

        FieldMeta f1 = new FieldMeta();
        f1.setName("firstname");
        f1.setTable(t1);
        f1.setType(String.class);
        f1.setTableName(t1.getName());
        t1.getFields().add(f1);

        FieldMeta f2 = new FieldMeta();
        f2.setName("age");
        f2.setTable(t1);
        f2.setType(Long.class);
        f2.setTableName(t1.getName());
        t1.getFields().add(f2);

        tables.add(t1);
        model.setTables(tables);

        sessionFactory.initWithMetaMappingBinder2(model);

        Session s = sessionFactory.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        Map<String, Object> map = new HashMap<>();
        map.put("firstname", "123");
        map.put("age", 10l);
        s.save("Employee", map);
        DetachedCriteria dc = DetachedCriteria.forEntityName("Employee");
        Criteria c = dc.getExecutableCriteria(s);
        assertTrue(c.list().size() > 0);
        tx.commit();
    }


}
