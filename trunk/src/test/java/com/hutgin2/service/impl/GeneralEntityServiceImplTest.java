package com.hutgin2.service.impl;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.MetaModel;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;
import com.hutgin2.service.GeneralEntityService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring/application.xml")
public class GeneralEntityServiceImplTest {

    @Autowired
    private GeneralEntityService generalEntityTransactionalService;

    @Autowired
    private MetaModel metaModel;

    @Test
    public void test() {
        TableMeta t1 = metaModel.getModel().getTable("Employee");
        EntityCollection ec = generalEntityTransactionalService.findAll(t1);
        int size = ec.size();
        Entity entity = new Entity(t1);
        entity.put("firstname", RandomStringUtils.random(10));
        entity.put("age", 10l);
        Assert.isTrue(generalEntityTransactionalService.save(entity));
        ec = generalEntityTransactionalService.findAll(t1);
        Assert.isTrue(ec.size() == size + 1);
        boolean found = false;
        for (Map<String, Object> e : ec) {
            if (StringUtils.equals((String) e.get("firstname"), (String) entity.get("firstname"))) {
                found = true;
                entity = new Entity(e, ec.getMeta());
                break;
            }
        }
        Assert.isTrue(found);
        entity = generalEntityTransactionalService.find(entity.getMeta(), (Serializable) entity.get("id"));
        Assert.notNull(entity);
        Assert.isTrue(generalEntityTransactionalService.remove(entity));
        ec = generalEntityTransactionalService.findAll(t1);
        Assert.isTrue(ec.size() == size);
        found = false;
        for (Map<String, Object> e : ec) {
            if (StringUtils.equals((String) e.get("firstname"), (String) entity.get("firstname"))) {
                found = true;
                break;
            }
        }
        Assert.isTrue(!found);
    }

}
