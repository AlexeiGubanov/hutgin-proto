package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.meta.TableMeta;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.Map;

public class TableMetaRowRenderer implements RowRenderer<TableMeta> {

    private Grid tablesList;

    public TableMetaRowRenderer(Grid tablesList) {
        this.tablesList = tablesList;
    }

    @Override
    public void render(Row row, final TableMeta table, int index) throws Exception {
        row.appendChild(new Checkbox());
        row.appendChild(new Label(table.getName()));
        row.appendChild(new Label(table.getTableName()));
        row.addEventListener("onDoubleClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                //create a window programmatically and use it as a modal dialog.
                Map<String, Object> params = new HashMap<>();
                params.put("action", "edit");
                params.put("entity", table);
                params.put("grid", tablesList);

                Window window = (Window) Executions.createComponents(
                        "/zul/components/admin/tableForm.zul", null, params);
                window.doModal();
            }
        });

    }
}
