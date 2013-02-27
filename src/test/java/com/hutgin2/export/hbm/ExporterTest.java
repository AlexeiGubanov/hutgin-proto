package com.hutgin2.export.hbm;

import com.hutgin2.meta.DatabaseModel;
import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExporterTest {
    @Test
    public void testExport() throws Exception {
        DatabaseModel model = new DatabaseModel();
        List<TableMeta> tables = new ArrayList<>();

        TableMeta t1 = new TableMeta("Employee");
        FieldMeta f1 = new FieldMeta();
        f1.setName("firstname");
        f1.setType(String.class);
        t1.getFields().add(f1);

        tables.add(t1);
        model.setTables(tables);
        new Exporter().export(model, "e:/test1.hbm.xml");
    }
}
