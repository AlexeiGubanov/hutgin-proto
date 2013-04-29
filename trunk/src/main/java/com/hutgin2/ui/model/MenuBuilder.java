package com.hutgin2.ui.model;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MenuBuilder implements IMenuBuilder {

    public static final String MENU_TABLES = "tables";
    public static final Long ID_MENU_TABLES = 10002l;

    @Autowired
    private TableMetaService tableMetaService;

    @Override
    public LinkedList<Menu> getMenu() {
        LinkedList<Menu> menus = new LinkedList<>();
        // 1. list tables
        Menu tables = new Menu();
        tables.setId(ID_MENU_TABLES);
        tables.setName(MENU_TABLES); //TODO l10n
        List<TableMeta> tableList = tableMetaService.findAll();
        for (TableMeta tm : tableList) {
            Menu m1 = new Menu();
            m1.setName(tm.getName());
            tables.addChild(m1);
        }
        menus.add(tables);

        return menus;


    }

}
