package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.MetaModel;
import com.hutgin2.entity.Entity;
import com.hutgin2.service.GeneralEntityService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class GeneralEntityServiceImplTest {

    @Autowired
    private MetaModel metaModel;

    @Autowired
    private GeneralEntityService entityService;

    @Test
    public void testGetAll() throws Exception {
        TableMeta t1 = metaModel.getModel().getTable("Employee");
        Entity t = entityService.find(t1, 1l);
        Assert.assertNotNull(t);


    }
}
