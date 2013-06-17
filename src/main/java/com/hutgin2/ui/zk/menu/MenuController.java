package com.hutgin2.ui.zk.menu;

import com.hutgin2.ui.model.IMenuBuilder;
import com.hutgin2.ui.zk.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.XulElement;

import java.util.List;
import java.util.Map;

@Component("mainMenuCtrl")
@Scope("desktop")
public class MenuController extends GenericForwardComposer {

    @Autowired
    private IMenuBuilder menuBuilder;
    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * All the components that are defined here and have a corresponding
     * component with the same 'id' in the zul-file are getting autowired by our
     * 'extends BaseCtrl' class wich extends Window and implements AfterCompose.
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    private Window mainMenuWindow; // autowire the IDSpace

    private Tree mainMenuTree;

    // Controllers
    private IndexController indexCtrl;

    protected transient Map<String, Object> args;

    public void doOnCreateCommon(Window w, Event fe) throws Exception {
        final CreateEvent ce = (CreateEvent) ((ForwardEvent) fe).getOrigin();
        this.args = (Map<String, Object>) ce.getArg();
    }

    public void onCreate$mainMenuWindow(Event event) throws Exception {

        doOnCreateCommon(this.mainMenuWindow, event); // do the autowire stuff

		/*
         * Overhand this controller to the caller.
		 */
        if (args.containsKey("indexController")) {
            this.indexCtrl = (IndexController) args.get("indexController");
            // SET THIS CONTROLLER TO THE module's Parent/MainController
            //TODO getIndexCtrl().setMainMenuCtrl(this);
        }

//        createMenu();
    }


    private void populateMenu(XulElement parent, List<com.hutgin2.ui.model.Menu> menus) {
        for (com.hutgin2.ui.model.Menu mi : menus) {

            final Treeitem treeitem = new Treeitem();
            parent.appendChild(treeitem);
            treeitem.setLabel(mi.getName());

//            final Treecell item = insertTreeCell(treeitem);


//            if (mi.getChilds() != null && mi.getChilds().size() > 0) {
//                Menu menu = new Menu();
//                menu.setLabel(mi.getName());
//                menu.setParent(parent);
//                Menupopup menupopup = new Menupopup();
//                menupopup.setParent(menu);
//                populateMenu(menupopup, mi.getChilds());
//            } else {
//                Menuitem menu = new Menuitem();
//                menu.setLabel(mi.getName());
//                menu.setParent(parent);
//
//            }
        }
    }

    /**
     * Creates the mainMenu. <br>
     *
     * @throws InterruptedException
     */
    private void createMenu() throws InterruptedException {

        Toolbarbutton toolbarbutton;

        final Panelchildren gb = (Panelchildren) this.mainMenuWindow.getFellowIfAny("panelChildren_menu");

        Space space = new Space();
        space.setHeight("5px");
        space.setParent(gb);

        // Hbox for the expand/collapse buttons
        final Hbox hbox = new Hbox();
        // hbox.setStyle("backgound-color: " + bgColorInner);
        hbox.setParent(gb);

        // ToolbarButton for expanding the menutree
//        toolbarbutton = new Toolbarbutton();
//        hbox.appendChild(toolbarbutton);
//        toolbarbutton.setId("btnMainMenuExpandAll");
//        toolbarbutton.setImage("/images/icons/folder_open_16x16.gif");
//        toolbarbutton.setTooltiptext(Labels.getLabel("btnFolderExpand.tooltiptext"));
//        toolbarbutton.addEventListener("onClick", new EventListener() {
//            @Override
//            public void onEvent(Event event) throws Exception {
//                onClick$btnMainMenuExpandAll(event);
//            }
//        });
//        toolbarbutton = new Toolbarbutton();
//        hbox.appendChild(toolbarbutton);
//        toolbarbutton.setId("btnMainMenuCollapseAll");
//        toolbarbutton.setImage("/images/icons/folder_closed2_16x16.gif");
//        toolbarbutton.setTooltiptext(Labels.getLabel("btnFolderCollapse.tooltiptext"));
//        toolbarbutton.addEventListener("onClick", new EventListener() {
//            @Override
//            public void onEvent(Event event) throws Exception {
//                onClick$btnMainMenuCollapseAll(event);
//            }
//        });

//        toolbarbutton = new Toolbarbutton();
//        hbox.appendChild(toolbarbutton);
//        toolbarbutton.setId("btnMainMenuChange");
//
//        toolbarbutton.setImage("/images/icons/menu_16x16.gif");
//        // toolbarbutton.setImage("/images/icons/combobox_16x16.gif");
//        toolbarbutton.setTooltiptext(Labels.getLabel("btnMainMenuChange.tooltiptext"));
//        toolbarbutton.addEventListener("onClick", new EventListener() {
//            @Override
//            public void onEvent(Event event) throws Exception {
//                onClick$btnMainMenuChange(event);
//            }
//        });


        Separator separator = createSeparator(false);
        separator.setWidth("97%");
        separator.setBar(false);
        separator.setParent(gb);

        separator = createSeparator(false);
        separator.setWidth("97%");
        separator.setBar(true);
        separator.setParent(gb);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

        // the menuTree
        mainMenuTree = new Tree();
        // tree.setSizedByContent(true);
        // tree.setZclass("z-dottree");
        mainMenuTree.setStyle("overflow:auto; border: none;");
        mainMenuTree.setParent(gb);

        final Treechildren treechildren = new Treechildren();
        mainMenuTree.appendChild(treechildren);

        // generate the treeMenu from the menuXMLFile
        // ZkossTreeMenuFactory.addMainMenu(treechildren);
        populateMenu(treechildren, menuBuilder.getMenu());

        final Separator sep1 = new Separator();
        sep1.setWidth("97%");
        sep1.setBar(false);
        sep1.setParent(gb);

		/* as standard, call the dashboard page */
//TODO        showPage("/WEB-INF/pages/dashboard.zul", "menu_Item_Home", Labels.getLabel("menu_Item_Home"));
    }

    /**
     * Creates a seperator. <br>
     *
     * @param withBar <br>
     *                true=with Bar <br>
     *                false = without Bar <br>
     * @return
     */
    private static Separator createSeparator(boolean withBar) {

        final Separator sep = new Separator();
        sep.setBar(withBar);

        return sep;
    }


//    private void showPage(String zulFilePathName, String tabID, String tabLabel) throws InterruptedException {
//
//        try {
//            // TODO get the parameter for working with tabs from the application
//            // params
//            final int workWithTabs = 1;
//
//            if (workWithTabs == 1) {
//
//				/* get an instance of the borderlayout defined in the zul-file */
//                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
//				/* get an instance of the searched CENTER layout area */
//                final Center center = bl.getCenter();
//                // get the tabs component
//                final Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");
//
//                /**
//                 * Check if the tab is already opened than select them and<br>
//                 * go out of here. If not than create them.<br>
//                 */
//
//                Tab checkTab = null;
//                try {
//                    // checkTab = (Tab) tabs.getFellow(tabName);
//                    checkTab = (Tab) tabs.getFellow("tab_" + tabID.trim());
//                    checkTab.setSelected(true);
//                } catch (final ComponentNotFoundException ex) {
//                    // Ignore if can not get tab.
//                }
//
//                if (checkTab == null) {
//
//                    final Tab tab = new Tab();
//                    tab.setId("tab_" + tabID.trim());
//
//                    if (tabLabel != null) {
//                        tab.setLabel(tabLabel.trim());
//                    } else {
//                        tab.setLabel(tabLabel.trim());
//                    }
//                    tab.setClosable(true);
//                    tab.setParent(tabs);
//
//                    final Tabpanels tabpanels = (Tabpanels) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter")
//                            .getFellow("tabpanelsBoxIndexCenter");
//                    final Tabpanel tabpanel = new Tabpanel();
//                    tabpanel.setHeight("100%");
//                    tabpanel.setStyle("padding: 0px;");
//                    tabpanel.setParent(tabpanels);
//
//					/*
//					 * create the page and put it in the tabs area
//					 */
//                    Executions.createComponents(zulFilePathName, tabpanel, null);
//                    tab.setSelected(true);
//                }
//            } else {
//				/* get an instance of the borderlayout defined in the zul-file */
//                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
//				/* get an instance of the searched CENTER layout area */
//                final Center center = bl.getCenter();
//				/* clear old center child comps */
//                center.getChildren().clear();
//				/*
//				 * create the page and put it in the center layout area
//				 */
//                Executions.createComponents(zulFilePathName, center, null);
//            }
//        } catch (final Exception e) {
//            Messagebox.show(e.toString());
//        }
//    }

//    public void onClick$btnMainMenuExpandAll(Event event) throws Exception {
//
////        if (logger.isDebugEnabled()) {
////            logger.debug("--> " + event.toString());
////        }
//        doCollapseExpandAll(getMainMenuWindow(), true);
//    }

//
//    public void onClick$btnMainMenuCollapseAll(Event event) throws Exception {
//
////        if (logger.isDebugEnabled()) {
////            logger.debug("--> " + event.toString());
////        }
//        doCollapseExpandAll(getMainMenuWindow(), false);
//    }
//
//    public void onClick$btnMainMenuChange(Event event) throws Exception {
//
//        // set the MenuOffset for correct calculating content height.
//        final Checkbox cb = (Checkbox) Path.getComponent("/outerIndexWindow/CBtreeMenu");
//        cb.setChecked(false);
//        UserWorkspace.getInstance().setTreeMenu(false);
//
//        // get an instance of the borderlayout defined in the index.zul-file
//        final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
//        // get an instance of the searched west layout area
//        final West west = bl.getWest();
//        west.setVisible(false);
//
//        final North north = bl.getNorth();
//        north.setHeight(north.getHeight() + "px");
//
//        final Div div = (Div) north.getFellow("divDropDownMenu");
//
//        final Menubar menuBar = (Menubar) div.getFellow("mainMenuBar");
//        menuBar.setVisible(true);
//
//        // generate the menu from the menuXMLFile
//        getDropDownMenuFactory().addMenu(menuBar);
//
//        final Menuitem changeToTreeMenu = new Menuitem();
//        changeToTreeMenu.setLabel(Labels.getLabel("menu_Item_backToTree"));
//        changeToTreeMenu.setImage("/images/icons/refresh2_yellow_16x16.gif");
//        changeToTreeMenu.setParent(menuBar);
//        changeToTreeMenu.addEventListener("onClick", new EventListener() {
//            @Override
//            public void onEvent(Event event) throws Exception {
//                // get an instance of the borderlayout defined in the
//                // index.zul-file
//                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
//                // get an instance of the searched west layout area
//                final West west = bl.getWest();
//                west.setVisible(true);
//
//                final North north = bl.getNorth();
//
//                final Div div = (Div) north.getFellow("divDropDownMenu");
//
//                final Menubar menuBar = (Menubar) div.getFellow("mainMenuBar");
//                menuBar.getChildren().clear();
//                menuBar.setVisible(false);
//
//                // set the MenuOffset for correct calculating content height.
//                final Checkbox cb = (Checkbox) Path.getComponent("/outerIndexWindow/CBtreeMenu");
//                cb.setChecked(true);
//                UserWorkspace.getInstance().setTreeMenu(true);
//
//                // Refresh the whole page for setting correct sizes of the
//                // components
//                final Window win = (Window) Path.getComponent("/outerIndexWindow");
//                win.invalidate();
//
//            }
//        });
//
//        // Refresh the whole page for setting correct sizes of the
//        // components
//        final Window win = (Window) Path.getComponent("/outerIndexWindow");
//        win.invalidate();
//    }
//
//    private void doCollapseExpandAll(org.zkoss.zk.ui.Component component, boolean aufklappen) {
//        if (component instanceof Treeitem) {
//            final Treeitem treeitem = (Treeitem) component;
//            treeitem.setOpen(aufklappen);
//        }
//        final Collection<?> com = component.getChildren();
//        if (com != null) {
//            for (final Iterator<?> iterator = com.iterator(); iterator.hasNext();) {
//                doCollapseExpandAll((org.zkoss.zk.ui.Component) iterator.next(), aufklappen);
//
//            }
//        }
//    }


}
