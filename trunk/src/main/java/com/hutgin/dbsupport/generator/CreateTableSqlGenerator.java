package com.hutgin.dbsupport.generator;

import com.hutgin.dbsupport.database.Database;
import com.hutgin.dbsupport.meta.Column;
import com.hutgin.dbsupport.meta.TableMeta;
import com.hutgin.dbsupport.meta.constraint.Constraint;
import com.hutgin.dbsupport.meta.constraint.ForeignKeyConstraint;
import com.hutgin.dbsupport.meta.constraint.PrimaryKeyConstraint;
import com.hutgin.dbsupport.meta.constraint.UniqueConstraint;

import java.util.List;

public class CreateTableSqlGenerator implements SqlGenerator {

    private static final String CREATE_TABLE = "CREATE TABLE %1$s(%2$s)";
    private static final String NOT_NULL = " NOT NULL";
    private static final String DEFAULT = " DEFAULT %1$s";
    private static final String COLUMN = "%1$s %2$s%3$s%4$s";
    private static final String CONSTRAINT_PRIMARY_KEY = "CONSTRAINT %1$s PRIMARY KEY (%2$s)";
    private static final String CONSTRAINT_UNIQUE = "CONSTRAINT %1$s UNIQUE (%2$s)";
    private static final String CONSTRAINT_FOREIGN_KEY = "CONSTRAINT %1$s FOREIGN KEY (%2$s) REFERENCES %3$s(%4$s)";

    private Database database;

    private TableMeta table;

    public CreateTableSqlGenerator(Database database, TableMeta table) {
        this.database = database;
        this.table = table;
    }

    @Override
    public String getSql() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (Column c : table.getColumns()) {
            if (cnt > 0) sb.append(",");
            String notNullSql = c.isRequired() ? NOT_NULL : "";
            String defValueSql = c.getDefaultValue() != null ? String.format(DEFAULT, c.getType().getValueAsString(c.getDefaultValue())) : "";
            String sql = String.format(COLUMN, database.escapeColumnName(c.getName()), c.getType().toSql(), defValueSql, notNullSql);
            sb.append(sql.trim());
            cnt++;
        }

        for (Constraint c : table.getConstraints()) {
            if (c instanceof PrimaryKeyConstraint) {
                if (cnt > 0) sb.append(",");
                PrimaryKeyConstraint cc = (PrimaryKeyConstraint) c;
                String sql = String.format(CONSTRAINT_PRIMARY_KEY, database.escapeName(cc.getName()), getColumnsAsString(cc.getColumns()));
                sb.append(sql.trim());
                cnt++;
            } else if (c instanceof ForeignKeyConstraint) {
                if (cnt > 0) sb.append(",");
                ForeignKeyConstraint cc = (ForeignKeyConstraint) c;
                String sql = String.format(CONSTRAINT_FOREIGN_KEY, database.escapeName(cc.getName()), getColumnsAsString(cc.getColumns()), database.escapeTableName(cc.getToTable().getSchema(), cc.getToTable().getName()), getColumnsAsString(cc.getReferences()));
                sb.append(sql.trim());
                cnt++;
            } else if (c instanceof UniqueConstraint) {
                if (cnt > 0) sb.append(",");
                UniqueConstraint cc = (UniqueConstraint) c;
                String sql = String.format(CONSTRAINT_UNIQUE, database.escapeName(cc.getName()), getColumnsAsString(cc.getColumns()));
                sb.append(sql.trim());
                cnt++;
            }
        }
        String sql = String.format(CREATE_TABLE, database.escapeTableName(table.getSchema(), table.getName()), sb.toString());
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
