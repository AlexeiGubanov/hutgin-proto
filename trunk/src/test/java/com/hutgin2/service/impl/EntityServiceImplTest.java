package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.MetaModel;
import com.hutgin2.service.EntityService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class EntityServiceImplTest {

    @Autowired
    private MetaModel metaModel;

    @Autowired
    private EntityService entityService;

    @Test
    public void testGetAll() throws Exception {
        TableMeta t1 = metaModel.getModel().getTables().get(0);
        List t = entityService.getAll(t1);
        int size = t.size();
        Map<String, Object> e1 = new HashMap<>();
        e1.put("firstname", "123");
        e1.put("age", 10l);
        entityService.save(t1, e1);

        Assert.assertEquals(entityService.getAll(t1).size(), size + 1);


    }
}
