package com.hutgin2.dao.hibernate;

import com.hutgin2.dao.TableDao;
import com.hutgin2.meta.TableMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class TableDaoImplTest {

    @Autowired
    private TableDao tableDao;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        assertThat(0, equalTo(tableDao.getAll().size()));
        TableMeta tm = new TableMeta("TABLE1");
        tableDao.persist(tm);
        assertThat(1, equalTo(tableDao.getAll().size()));


    }


}
