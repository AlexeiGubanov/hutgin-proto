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

//    @Override
//    public void doBeforeComposeChildren(Borderlayout comp) throws Exception {
//        super.doBeforeComposeChildren(comp);
//        final North north = new North();
//        north.setHeight("100px;");
//        north.setBorder("none");
//        north.setParent(br);
//        new Label("North").setParent(north);
//
//        //MENU
////        LinkedList<com.hutgin2.ui.model.Menu> menus = menuBuilder.getMenu();
////        Menubar menubar = new Menubar(); //def hor
////        populateMenu(menubar, menus);
////        menubar.setParent(north);
//
//
//        final West west = new West();
//        west.setWidth("260px;");
//        west.setBorder("none");
//        west.setCollapsible(true);
//        west.setSplittable(true);
//        west.setMinsize(300);
//        west.setParent(br);
//        new Label("West").setParent(west);
//
//        final Center center = new Center();
//        center.setId("mainContent");
//        center.setAutoscroll(true);
//        center.setParent(br);
//        new Label("Center").setParent(center);
//
//        final South south = new South();
//        south.setHeight("50px");
//        south.setBorder("none");
//        south.setParent(br);
//        new Label("South").setParent(south);
//    }
}
