package com.hutgin2.core.service;

import com.hutgin2.core.meta.FieldMeta;
import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.meta.ValueGenerationStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class TableMetaServiceTest {

    @Autowired
    private TableMetaService tableMetaService;

    @Test
    public void testGetAll() throws Exception {
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

        tableMetaService.save(t1);

        assertTrue(tableMetaService.getAll().size() > 0);

    }
}
