package com.hutgin2.ui.zk;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.West;

import java.util.HashMap;

@Component("indexCtrl")
@Scope("desktop")
public class IndexController extends GenericForwardComposer {

    protected Menubar mainMenuBar; // autowired
    protected Tabs tabsIndexCenter; // autowired


    private void createMainTreeMenu(Event event) {
        // get an instance of the borderlayout defined in the index.zul-file
        Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");

        // get an instance of the searched west layout area
        West west = bl.getWest();
        // clear the WEST child comps
        west.getChildren().clear();

        HashMap<String, Object> map = new HashMap<>();
        map.put("indexController", this);

        Executions.createComponents("/zul/menu/mainMenu.zul", west, map);
    }

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
}
