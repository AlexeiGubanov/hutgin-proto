package com.hutgin.dbsupport.generator;

import com.hutgin.dbsupport.database.AbstractDatabase;
import com.hutgin.dbsupport.database.Database;
import com.hutgin.dbsupport.meta.Column;
import com.hutgin.dbsupport.meta.TableMeta;
import com.hutgin.dbsupport.meta.constraint.ForeignKeyConstraint;
import com.hutgin.dbsupport.meta.constraint.PrimaryKeyConstraint;
import com.hutgin.dbsupport.meta.constraint.UniqueConstraint;
import com.hutgin.dbsupport.meta.datatype.IntegerDataType;
import com.hutgin.dbsupport.meta.datatype.VarcharDataType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CreateTableSqlGeneratorTest {
    @Test
    public void testGetSql() throws Exception {
        TableMeta table = new TableMeta("schema", "name");

        Database db = new AbstractDatabase();
        CreateTableSqlGenerator g = new CreateTableSqlGenerator(db, table);

        Column id = new Column("ID", new IntegerDataType());
        id.setRequired(true);
        table.addColumn(id);
        assertThat(g.getSql(), equalTo("CREATE TABLE name(ID INTEGER NOT NULL)"));

        Column name = new Column("name", new VarcharDataType(50));
        name.setRequired(true);
        table.addColumn(name);
        assertThat(g.getSql(), equalTo("CREATE TABLE name(ID INTEGER NOT NULL,name VARCHAR(50) NOT NULL)"));

        Column value = new Column("value", new VarcharDataType(255));
        value.setDefaultValue("");
        table.addColumn(value);
        assertThat(g.getSql(), equalTo("CREATE TABLE name(ID INTEGER NOT NULL,name VARCHAR(50) NOT NULL,value VARCHAR(255) DEFAULT '')"));

        PrimaryKeyConstraint pk = new PrimaryKeyConstraint("PK_ID", id);
        table.addConstraint(pk);
        assertThat(g.getSql(), equalTo("CREATE TABLE name(ID INTEGER NOT NULL,name VARCHAR(50) NOT NULL,value VARCHAR(255) DEFAULT '',CONSTRAINT PK_ID PRIMARY KEY (ID))"));

        UniqueConstraint uq = new UniqueConstraint("UQ_NAME", name);
        table.addConstraint(uq);
        assertThat(g.getSql(), equalTo("CREATE TABLE name(ID INTEGER NOT NULL,name VARCHAR(50) NOT NULL,value VARCHAR(255) DEFAULT '',CONSTRAINT PK_ID PRIMARY KEY (ID),CONSTRAINT UQ_NAME UNIQUE (name))"));


        TableMeta toTable = new TableMeta("table1");
        Column refColumn = new Column("refCol", new IntegerDataType());
        ForeignKeyConstraint fk = new ForeignKeyConstraint("FK_ID", id, toTable, refColumn);
        table.addConstraint(fk);
        assertThat(g.getSql(), equalTo("CREATE TABLE name(ID INTEGER NOT NULL,name VARCHAR(50) NOT NULL,value VARCHAR(255) DEFAULT '',CONSTRAINT PK_ID PRIMARY KEY (ID),CONSTRAINT UQ_NAME UNIQUE (name),CONSTRAINT FK_ID FOREIGN KEY (ID) REFERENCES table1(refCol))"));

    }


}
