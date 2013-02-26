package com.hutgin2.export.hbm;

import com.hutgin2.meta.FieldDataType;
import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExporterTest {
    @Test
    public void testExport() throws Exception {
        Exporter e = new Exporter();
        List<TableMeta> tables = new ArrayList<>();

        TableMeta t1 = new TableMeta("table1");
        FieldMeta f = new FieldMeta();
        f.setName("col1");
        f.setType(FieldDataType.Integer);
        t1.getFields().add(f);
        f = new FieldMeta();
        f.setName("col2");
        f.setType(FieldDataType.String);
        t1.getFields().add(f);
        tables.add(t1);

        TableMeta t2 = new TableMeta("table2");
        f = new FieldMeta();
        f.setName("col3");
        f.setType(FieldDataType.Integer);
        t2.getFields().add(f);
        f = new FieldMeta();
        f.setName("col4");
        f.setType(FieldDataType.String);
        t2.getFields().add(f);
        tables.add(t2);

        e.export(tables, "e:/test1.hbm.xml");

    }
}
