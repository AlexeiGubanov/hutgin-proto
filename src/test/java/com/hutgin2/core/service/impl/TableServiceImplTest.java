package com.hutgin2.core.service.impl;

import com.hutgin2.core.service.TableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class TableServiceImplTest {

    @Autowired
    private TableService tableService;

    @Test
    public void testGetAll() throws Exception {
        assertThat(0, equalTo(tableService.getAll().size()));
    }
}
