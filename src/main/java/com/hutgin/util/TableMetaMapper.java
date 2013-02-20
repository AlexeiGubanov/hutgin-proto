package com.hutgin.util;

import com.hutgin.dbsupport.meta.TableMeta;
import com.hutgin.entity.Table;

public class TableMetaMapper {

    public static TableMeta toMeta(Table table) {
        TableMeta result = new TableMeta(table.getName());
        return result;

    }

}
