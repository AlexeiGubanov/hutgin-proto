package com.hutgin2.ui.zk;

import com.hutgin2.ui.model.IMenuBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.XulElement;

import java.util.LinkedList;
import java.util.List;


@Component("rootComposer")
@Scope("desktop")
public class RootComposer extends SelectorComposer<Borderlayout> {

    @Autowired
    private IMenuBuilder menuBuilder;
    private Borderlayout borderlayout;

    private North north;
    private West west;
    private Center center;
    private South south;

    private void createLayouts() {
        north = new North();
        north.setParent(borderlayout);
        north.setHeight("100px");

        west = new West();
        west.setParent(borderlayout);
        west.setWidth("200px");
        west.setCollapsible(true);
        west.setSplittable(true);

        LinkedList<com.hutgin2.ui.model.Menu> menus = menuBuilder.getMenu();
        Menubar menubar = new Menubar(); //def hor
        populateMenu(menubar, menus);
        menubar.setParent(north);


        center = new Center();
        center.setParent(borderlayout);
        center.setAutoscroll(true);

        Window w = new Window();
        w.setParent(center);

        south = new South();
        south.setParent(borderlayout);
        south.setHeight("70px");
    }

    private void createTabpanel() {

    }

    private void populateMenu(XulElement parent, List<com.hutgin2.ui.model.Menu> menus) {
        for (com.hutgin2.ui.model.Menu mi : menus) {
            if (mi.getChilds() != null && mi.getChilds().size() > 0) {
                Menu menu = new Menu();
                menu.setLabel(mi.getName());
                menu.setParent(parent);
                Menupopup menupopup = new Menupopup();
                menupopup.setParent(menu);
                populateMenu(menupopup, mi.getChilds());
            } else {
                Menuitem menu = new Menuitem();
                menu.setLabel(mi.getName());
                menu.setParent(parent);

            }
        }
    }


    @Override
    public void doAfterCompose(Borderlayout borderlayout) throws Exception {
        this.borderlayout = borderlayout;
//        createLayouts();
    }

}
