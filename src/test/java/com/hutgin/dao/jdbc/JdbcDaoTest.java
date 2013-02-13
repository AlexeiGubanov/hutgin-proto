package com.hutgin.dao.jdbc;

import com.hutgin.entity.Entity;
import com.hutgin.entity.Field;
import com.hutgin.entity.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:conf/spring/application.xml")
public class JdbcDaoTest {

    private EmbeddedDatabase db;

    private JdbcDao dao;

    @Before
    public void setUp() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        dbBuilder.setType(EmbeddedDatabaseType.H2);
        dbBuilder.addScript("classpath:/testData/jdbcDaoTest/shema.sql");
        dbBuilder.addScript("classpath:/testData/jdbcDaoTest/data.sql");
        db = dbBuilder.build();
        dao = new JdbcDao();
        dao.setDataSource(db);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void testFindAll() throws Exception {
        Table t = new Table("test1");
        t.addField(new Field("id"));
        t.addField(new Field("name"));
        t.addField(new Field("value"));

        List<Entity> origin = new ArrayList<>();
        Entity item1 = new Entity();
        item1.put("ID", 1);
        item1.put("NAME", "name1");
        item1.put("VALUE", "val1");
        origin.add(item1);
        Entity item2 = new Entity();
        item2.put("ID", 2);
        item2.put("NAME", "name2");
        item2.put("VALUE", "val2");
        origin.add(item2);

        List<Entity> list = dao.findAll(t);
        assertThat(list.size(), equalTo(2));
        assertThat(list, hasItem(item1));
        assertThat(list, hasItem(item2));
    }

    @Test
    public void testjOOQ() {
//        Factory create = new Factory(db, SQLDialect.MYSQL);
//        Result<Record> result = create.select().from().fetch();

    }
}
