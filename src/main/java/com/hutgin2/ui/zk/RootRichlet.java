package com.hutgin2.ui.zk;

import com.hutgin2.ui.model.IMenuBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.XulElement;

import java.util.List;

@Component
@Deprecated
/**
 * Cannot be used because autowire isn't working (or just I don't know how to inject it here)
 */
public class RootRichlet extends GenericRichlet {

    @Autowired
    private IMenuBuilder menuBuilder;// = (IMenuBuilder) SpringUtil.getBean("menuBuilder");

    private void populateMenu(XulElement parent, List<com.hutgin2.ui.model.Menu> menus) {
        for (com.hutgin2.ui.model.Menu mi : menus) {
            Menu menu = new Menu();
            menu.setLabel(mi.getName());
            menu.setParent(parent);
            if (mi.getChilds() != null) {
                populateMenu(menu, mi.getChilds());
            }
        }
    }

    @Override
    public void service(Page page) throws Exception {

        page.setTitle("Hutgin"); // TODO render context specific
//        final Window w = new Window(null, "normal", false);
        final Borderlayout br = new Borderlayout();
        br.setHflex("1");
        br.setVflex("1");
//        br.setParent(w);

        final North north = new North();
        north.setHeight("100px;");
        north.setBorder("none");
        north.setParent(br);
        new Label("North").setParent(north);

        //MENU
//        LinkedList<com.hutgin2.ui.model.Menu> menus = menuBuilder.getMenu();
//        Menubar menubar = new Menubar(); //def hor
//        populateMenu(menubar, menus);
//        menubar.setParent(north);


        final West west = new West();
        west.setWidth("260px;");
        west.setBorder("none");
        west.setCollapsible(true);
        west.setSplittable(true);
        west.setMinsize(300);
        west.setParent(br);
        new Label("West").setParent(west);

        final Center center = new Center();
        center.setId("mainContent");
        center.setAutoscroll(true);
        center.setParent(br);
        new Label("Center").setParent(center);

        final South south = new South();
        south.setHeight("50px");
        south.setBorder("none");
        south.setParent(br);
        new Label("South").setParent(south);
//        <label style="font-size:50px">North for header</label>
//        <label style="font-size:50px">West for sidebar</label>
//        <label style="font-size:50px">Center for content</label>
//        <label style="font-size:50px">South for footer</label>
        br.setPage(page);
    }
}
