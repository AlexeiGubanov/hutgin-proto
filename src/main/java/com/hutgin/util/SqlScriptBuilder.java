package com.hutgin.util;

import com.hutgin.entity.Field;
import com.hutgin.entity.Table;

public class SqlScriptBuilder {

    public static String getSelectAllSql(Table type) {
        StringBuilder sql = new StringBuilder();
        for (Field field : type.getFields()) {
            if (sql.length() > 0) sql.append(",");
            sql.append(field.getName());
        }
        sql.insert(0, "SELECT ");
        sql.append(" FROM ");
        sql.append(type.getName());
        return sql.toString();

    }

}
