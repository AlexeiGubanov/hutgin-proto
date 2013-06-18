package com.hutgin2.ui.zk;

import com.hutgin2.ui.components.IComponentFactory;
import com.hutgin2.ui.components.INavigator;
import com.hutgin2.ui.model.menu.Menu;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.util.HashMap;

@Component("indexCtrl")
@Scope("desktop")
public class IndexController extends GenericForwardComposer implements INavigator {

    protected Menubar mainMenuBar; // autowired
    protected Tabs tabsMain; // autowired
    protected Tabpanels tabpanelsMain; // autowired

    private IComponentFactory componentFactory = new ZKComponentFactory();

    /**
     * Event listener for component id='outerIndexWindow', onCreate
     */
    public void onCreate$outerIndexWindow(Event event) throws Exception {
//        this.mainMenuBar.setVisible(false);

        createMainTreeMenu(event);
//
//        doDemoMode();
//
//        final String zkVersion = doGetZkVersion();
//        final String _version = this.appName + appVersion + ZksampleDateFormat.getDateFormater().format(buildDate);
//
//        final String userName = ((UserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        final String version = zkVersion + " | " + _version;
//        final String tenantId = "4711";
//        final String officeID = "39";
//        final String tableSchemaName = "public";
//
//        EventQueues.lookup("userNameEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeUser", null, userName));
//
//        EventQueues.lookup("tenantIdEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeTenant", null, tenantId));
//
//        EventQueues.lookup("officeIdEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeOfficeId", null, officeID));
//
//        EventQueues.lookup("appVersionEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeAppVersion", null, version));
//
//        EventQueues.lookup("tableSchemaEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeTableSchema", null, tableSchemaName));
//
//        /**
//         * Creates the ApplicationMessageQueue
//         */
//        new ApplicationMessageQueue();
//
//        doCreateDemoTimerForNotifications(event);
    }

    @Override
    public void navigate(Menu menu) {
        // this layout is working with tabs
        String tabName = menu.getName();

        // try to find open tab
        Tab tab = null;
        try {
            tab = (Tab) this.tabsMain.getFellow(tabName);
            tab.setSelected(true);
        } catch (final ComponentNotFoundException ex) {
            // ignored
        }

        if (tab == null) {
            // not found, creating new one
            tab = new Tab();
            tab.setId(tabName);
            tab.setLabel(tabName);
            tab.setClosable(true);
            tab.setParent(this.tabsMain);

            final Tabpanel tabpanel = new Tabpanel();
            tabpanel.setHeight("100%");
            tabpanel.setStyle("padding: 0px;");
            tabpanel.setParent(this.tabpanelsMain);

            componentFactory.createComponent(menu.getPath(), tabpanel, null); //TODO pass creation args
            tab.setSelected(true);
        }
    }

    private void createMainTreeMenu(Event event) {
        // get an instance of the borderlayout defined in the index.zul-file
        Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");

        // get an instance of the searched west layout area
        West west = bl.getWest();
        // clear the WEST child comps
        west.getChildren().clear();

        HashMap<String, Object> map = new HashMap<>();
        map.put(Attribute.NAVIGATOR, this);

        componentFactory.createComponent("/zul/components/mainMenu/treeMenu.zul", west, map);
    }
}
