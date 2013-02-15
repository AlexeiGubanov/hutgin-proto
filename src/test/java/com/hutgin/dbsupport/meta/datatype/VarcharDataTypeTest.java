package com.hutgin.dbsupport.meta.datatype;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class VarcharDataTypeTest {
    @Test
    public void testToSql() throws Exception {
        VarcharDataType type = new VarcharDataType();
        assertThat(type.getSize(), equalTo(255));
        assertThat(type.toSql(), equalTo("VARCHAR(255)"));
        type.setSize(50);
        assertThat(type.toSql(), equalTo("VARCHAR(50)"));

    }


    @Test
    public void testGetValueAsString() throws Exception {
        VarcharDataType type = new VarcharDataType();
        assertThat(type.getValueAsString("123"), equalTo("'123'"));
        assertThat(type.getValueAsString(""), equalTo("''"));


    }
}
