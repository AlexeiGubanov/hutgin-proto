package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Component("tablesController")
@Scope("desktop")
public class TablesController extends SelectorComposer<Component> {
    @Wire
    Grid tablesList;  // autowired
    @Wire
    Paging paging; // autowired

    private BindingListModelList<TableMeta> model;

    @Autowired
    private TableMetaService tableMetaService;

    @Listen("onCreate = #tablesList")
    public void onCreate$tablesList(Event event) {
        paging.setPageSize(10);
        paging.setPageIncrement(10);
        paging.setDetailed(true);

        tablesList.setRowRenderer(new TableMetaRowRenderer(new EventListenerBuilder() {
            @Override
            public EventListener getEventListener(final TableMeta tableMeta) {
                return new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        //create a window programmatically and use it as a modal dialog.
                        Map<String, Object> params = new HashMap<>();
                        params.put("action", "edit");
                        params.put("entity", tableMeta);
                        params.put("grid", tablesList);

                        Window win = (Window) Executions.createComponents("/zul/components/admin/tableForm.zul", null, params);
                        win.doModal();
                    }
                };
            }
        }));
        List<TableMeta> tables = tableMetaService.findAll();
        model = new BindingListModelList<>(tables, true); // todo provide mine  with paging and sorting
        tablesList.setModel(model);
    }

    @Listen("onClick = #btnNew")
    public void onClick$btnNew(Event e) {
        Map<String, Object> params = new HashMap<>();
        params.put("action", "create");
        params.put("grid", tablesList);
        Window win = (Window) Executions.createComponents("/zul/components/admin/tableForm.zul", null, params);
        win.doModal();

    }

    @Listen("onClick = #btnDelete")
    public void onClick$btnDelete(Event e) {
        // list all checked
    }

    public static interface EventListenerBuilder {
        EventListener getEventListener(TableMeta tableMeta);
    }


}
