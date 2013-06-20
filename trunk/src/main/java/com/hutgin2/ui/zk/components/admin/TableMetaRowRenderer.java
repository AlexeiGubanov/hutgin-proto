package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.meta.TableMeta;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class TableMetaRowRenderer implements RowRenderer<TableMeta> {

    private TablesController.EventListenerBuilder onDoubleClick;

    public TableMetaRowRenderer(TablesController.EventListenerBuilder onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
    }

    @Override
    public void render(Row row, final TableMeta table, int index) throws Exception {
        row.appendChild(new Checkbox());
        row.appendChild(new Label(table.getName()));
        row.appendChild(new Label(table.getTableName()));
        row.addEventListener("onDoubleClick", onDoubleClick.getEventListener(table));

    }
}
