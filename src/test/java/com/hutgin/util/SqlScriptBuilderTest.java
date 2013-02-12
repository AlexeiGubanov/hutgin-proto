package com.hutgin.util;

import com.hutgin.entity.Field;
import com.hutgin.entity.Table;
import org.junit.Assert;
import org.junit.Test;

public class SqlScriptBuilderTest {
    @Test
    public void testGetSelectAllSql() throws Exception {
        Table t = new Table();
        t.setName("tableName");
        t.addField(new Field("id"));
        t.addField(new Field("name"));
        t.addField(new Field("val"));

        String sql = "SELECT id,name,val FROM tableName";
        Assert.assertEquals(sql, SqlScriptBuilder.getSelectAllSql(t));

    }
}
