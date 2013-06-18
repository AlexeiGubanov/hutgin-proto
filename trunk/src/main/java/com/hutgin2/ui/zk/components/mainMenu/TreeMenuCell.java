package com.hutgin2.ui.zk.components.mainMenu;

import com.hutgin2.ui.components.INavigationMenuItem;
import com.hutgin2.ui.model.menu.Menu;
import org.zkoss.zul.Treecell;

public class TreeMenuCell extends Treecell implements INavigationMenuItem {
    private Menu menu;

    public TreeMenuCell(Menu menu) {
        this.menu = menu;
    }

    @Override
    public Menu getMenu() {
        return this.menu;
    }

    @Override
    public String getPath() {
        return this.menu.getPath();
    }
}
