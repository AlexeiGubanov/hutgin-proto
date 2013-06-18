package com.hutgin2.ui.model.menu;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MenuBuilder implements IMenuBuilder {

    public static final String MENU_ADMIN = "admin";
    public static final String MENU_TABLES = "tables";

    public static final Long MENU_ADMIN_ID = 10001l;
    public static final Long MENU_TABLES_ID = 10002l;


    @Autowired
    private TableMetaService tableMetaService;

    @Override
    public LinkedList<Menu> getMenu() {
        LinkedList<Menu> menus = new LinkedList<>();
        fillAdminMenu(menus);
        fillTablesMenu(menus);
        return menus;
    }

    private void fillTablesMenu(LinkedList<Menu> menus) {
        // 1. populate tables // TODO only if ROOT_TABLES menu is presented in DB MENU table
        Menu tables = new Menu();
        tables.setId(MENU_TABLES_ID);
        tables.setType(MenuType.GROUPING);
        tables.setName(MENU_TABLES); //TODO l10n
        List<TableMeta> tableList = tableMetaService.findAll();
        for (TableMeta tm : tableList) {
            Menu m1 = new Menu();
            m1.setName(tm.getName());
            m1.setType(MenuType.TABLE);
            m1.setPath("/tables/" + tm.getName());
            tables.addChild(m1);
        }
        menus.add(tables);
    }

    private void fillAdminMenu(LinkedList<Menu> menus) {
        // populate admin menus// TODO only if ADMIN_MENU menu is presented in DB MENU table
        Menu admins = new Menu();
        admins.setId(MENU_ADMIN_ID);
        admins.setType(MenuType.GROUPING);
        admins.setName(MENU_ADMIN); //TODO l10n
        //TODO retrieve from DB
//        for (TableMeta tm : tableList) {
        Menu m1 = new Menu();
        m1.setName("tables");
        m1.setType(MenuType.ADMIN);
        m1.setPath("/admin/" + "tables");
        admins.addChild(m1);

        Menu m2 = new Menu();
        m2.setName("menus");
        m2.setType(MenuType.ADMIN);
        m2.setPath("/admin/" + "menus");
        admins.addChild(m2);
//        }
        menus.add(admins);
    }

}
