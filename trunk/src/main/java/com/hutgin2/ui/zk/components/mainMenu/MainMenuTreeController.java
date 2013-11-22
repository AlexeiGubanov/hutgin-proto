package com.hutgin2.ui.zk.components.mainMenu;

import com.hutgin2.ui.components.INavigator;
import com.hutgin2.ui.model.menu.IMenuBuilder;
import com.hutgin2.ui.model.menu.Menu;
import com.hutgin2.ui.model.menu.MenuType;
import com.hutgin2.ui.zk.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component("mainTreeMenuCtrl")
@Scope("desktop")
public class MainMenuTreeController extends GenericForwardComposer {

    private static final Logger LOG = LoggerFactory.getLogger(MainMenuTreeController.class);

    @Autowired
    private IMenuBuilder menuBuilder;
    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * All the components that are defined here and have a corresponding
     * component with the same 'id' in the zul-file are getting autowired by our
     * 'extends BaseCtrl' class wich extends Window and implements AfterCompose.
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    private Tree mainTreeMenu; // autowired
    private Treechildren mainTreeMenuChildren; // autowired


    private INavigator navigator;

    /**
     * Event listener autowired by component name.
     */
    public void onCreate$mainTreeMenu(Event event) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onCreate$mainTreeMenu event:{}", event.toString());
        }
        final CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
        Map<String, Object> args = (Map<String, Object>) ce.getArg();
        if (args.containsKey(Attribute.NAVIGATOR)) {
            this.navigator = (INavigator) args.get(Attribute.NAVIGATOR);
        }

        createMenu();
    }

    private void createMenu() {
        // clear the current state
        this.mainTreeMenuChildren.getChildren().clear();

        LinkedList<Menu> menus = menuBuilder.getMenu();

        // recursively populate items
        fillMenuItems(this.mainTreeMenuChildren, menus);

    }

    /**
     * Recursively fill the mainMenu
     */
    private void fillMenuItems(Treechildren parent, List<Menu> menus) {
        for (Menu mi : menus) {
            // create treeitem
            Treeitem item = new Treeitem();
            item.setParent(parent);
            // create tree row
            Treerow row = new Treerow();
            row.setParent(item);
            // create tree cell
            final TreeMenuCell cell = new TreeMenuCell(mi);
            cell.setParent(row);
            cell.setLabel(mi.getName()); //TODO l10n
            if (mi.getType() != MenuType.GROUPING) {
                cell.addEventListener("onClick", new org.zkoss.zk.ui.event.EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        navigator.navigate(cell.getMenu());
                    }
                });
            }
            // check childs
            if (mi.getChilds() != null && mi.getChilds().size() > 0) {
                Treechildren childs = new Treechildren();
                childs.setParent(item);
                fillMenuItems(childs, mi.getChilds());
            }
        }
    }

    public void onClick$btnExpandAll(Event event) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onClick$btnExpandAll event:{}", event.toString());
        }
        doCollapseExpandAll(this.mainTreeMenuChildren, true);
    }

    public void onClick$btnCollapseAll(Event event) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onClick$btnCollapseAll event:{}", event.toString());
        }
        doCollapseExpandAll(this.mainTreeMenuChildren, false);
    }

    private void doCollapseExpandAll(org.zkoss.zk.ui.Component root, boolean doExpand) {
        final List<? extends org.zkoss.zk.ui.Component> childs = root.getChildren();
        for (org.zkoss.zk.ui.Component child : childs) {
            if (child instanceof Treeitem) {
                Treeitem treeitem = (Treeitem) child;
                treeitem.setOpen(doExpand);
            }
            doCollapseExpandAll(child, doExpand);
        }
    }


}
