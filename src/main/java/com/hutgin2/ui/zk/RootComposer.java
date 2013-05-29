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
//    @Wire
//    private North north;

    @Autowired
    private IMenuBuilder menuBuilder;


    private North north;
    private West west;
    private Center center;
    private South south;

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
    public void doAfterCompose(Borderlayout comp) throws Exception {
        super.doAfterCompose(comp);    //To change body of overridden methods use File | Settings | File Templates.


        north = new North();
        north.setParent(comp);
        north.setHeight("100px");

        west = new West();
        west.setParent(comp);
        west.setWidth("200px");
        west.setCollapsible(true);
        west.setSplittable(true);

        LinkedList<com.hutgin2.ui.model.Menu> menus = menuBuilder.getMenu();
        Menubar menubar = new Menubar(); //def hor
        populateMenu(menubar, menus);
        menubar.setParent(north);


        center = new Center();
        center.setParent(comp);
        center.setAutoscroll(true);

        Window w = new Window();
        w.setParent(center);

        south = new South();
        south.setParent(comp);
        south.setHeight("70px");


    }

}
