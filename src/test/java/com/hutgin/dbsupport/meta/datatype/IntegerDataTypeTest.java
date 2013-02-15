package com.hutgin.dbsupport.meta.datatype;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class IntegerDataTypeTest {
    @Test
    public void testToSql() throws Exception {
        IntegerDataType type = new IntegerDataType();
        assertThat(type.getSize(), equalTo(0));
        assertThat(type.toSql(), equalTo("INTEGER"));
        type.setSize(10);
        assertThat(type.toSql(), equalTo("INTEGER(10)"));
    }

    @Test
    public void testGetValueAsString() throws Exception {
        IntegerDataType type = new IntegerDataType();
        assertThat("10", equalTo(type.getValueAsString(10)));
    }
}
