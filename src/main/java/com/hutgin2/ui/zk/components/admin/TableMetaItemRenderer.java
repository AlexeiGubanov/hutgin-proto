package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.meta.TableMeta;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TableMetaItemRenderer implements ListitemRenderer<TableMeta> {

    @Override
    public void render(Listitem item, TableMeta data, int index) throws Exception {
        Listcell lc = new Listcell(data.getName());
        lc.setParent(item);
        lc = new Listcell(data.getTableName());
        lc.setParent(item);
        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedItem");
    }
}
