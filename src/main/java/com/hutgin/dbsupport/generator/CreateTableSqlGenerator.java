package com.hutgin.dbsupport.generator;

import com.hutgin.dbsupport.database.Database;
import com.hutgin.dbsupport.meta.Column;
import com.hutgin.dbsupport.meta.Table;
import com.hutgin.dbsupport.meta.constraint.Constraint;
import com.hutgin.dbsupport.meta.constraint.ForeignKeyConstraint;
import com.hutgin.dbsupport.meta.constraint.PrimaryKeyConstraint;
import com.hutgin.dbsupport.meta.constraint.UniqueConstraint;

import java.util.List;

public class CreateTableSqlGenerator implements SqlGenerator {

    private static final String CREATE_TABLE = "CREATE TABLE :tableName(:columns)";

    private Database database;

    private Table table;

    public CreateTableSqlGenerator(Database database, Table table) {
        this.database = database;
        this.table = table;
    }

    @Override
    public String getSql() {
        String sql = CREATE_TABLE;
        sql = sql.replace(":tableName", database.escapeTableName(table.getSchema(), table.getName()));

        // list columns and constraints
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (Column c : table.getColumns()) {
            if (cnt > 0) sb.append(",");
            sb.append(database.escapeColumnName(c.getName()));
            sb.append(" ");
            sb.append(c.getType().toSql());
            if (c.getDefaultValue() != null) {
                sb.append(" ").append("DEFAULT").append(" ").append(c.getType().getValueAsString(c.getDefaultValue()));
            }
            if (c.isRequired()) {
                sb.append(" ").append("NOT NULL");
            }
            cnt++;
        }

        for (Constraint c : table.getConstraints()) {
            if (c instanceof PrimaryKeyConstraint) {
                if (cnt > 0) sb.append(",");
                //CONSTRAINT pk_PersonID PRIMARY KEY (P_Id,LastName)
                PrimaryKeyConstraint cc = (PrimaryKeyConstraint) c;
                sb.append("CONSTRAINT").append(" ").append(database.escapeName(cc.getName())).append(" ");
                sb.append("PRIMARY KEY");
                sb.append("(");
                sb.append(getColumnsAsString(cc.getColumns()));
                sb.append(")");
                cnt++;
            } else if (c instanceof ForeignKeyConstraint) {
                if (cnt > 0) sb.append(",");
                //CONSTRAINT fk_PerOrders FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
                ForeignKeyConstraint cc = (ForeignKeyConstraint) c;
                sb.append("CONSTRAINT").append(" ").append(database.escapeName(cc.getName())).append(" ");
                sb.append("FOREIGN KEY");
                sb.append("(");
                sb.append(getColumnsAsString(cc.getColumns()));
                sb.append(")").append(" ");
                sb.append("REFERENCES").append(" ");
                sb.append(database.escapeTableName(cc.getToTable().getSchema(), cc.getToTable().getName()));
                sb.append("(");
                sb.append(getColumnsAsString(cc.getReferences()));
                sb.append(")");
                cnt++;
            } else if (c instanceof UniqueConstraint) {
                if (cnt > 0) sb.append(",");
                //CONSTRAINT uc_PersonID UNIQUE (P_Id,LastName)
                UniqueConstraint cc = (UniqueConstraint) c;
                sb.append("CONSTRAINT").append(" ").append(database.escapeName(cc.getName())).append(" ");
                sb.append("UNIQUE");
                sb.append("(");
                sb.append(getColumnsAsString(cc.getColumns()));
                sb.append(")");
                cnt++;
            }
        }
        sql = sql.replace(":columns", sb.toString());
        return sql;
    }

    private String getColumnsAsString(List<Column> columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(database.escapeColumnName(columns.get(i).getName()));
        }
        return sb.toString();

    }
}
