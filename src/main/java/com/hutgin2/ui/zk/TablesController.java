package com.hutgin2.ui.zk;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Component("tablesController")
@Scope("desktop")
public class TablesController extends SelectorComposer<Component> {

    @Wire
    Grid tablesList;


    @Autowired
    private TableMetaService tableMetaService;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        //initialize view after view construction.
        Rows rows = tablesList.getRows();

        List<TableMeta> tables = tableMetaService.findAll();

        for (TableMeta table : tables) {
            Row row = new Row();
            row.appendChild(new Checkbox());
            row.appendChild(new Label(table.getName()));
            row.appendChild(new Label(table.getTableName()));
            rows.appendChild(row);
        }
// sample of dynamic event listner
//        Toolbarbutton btnNew = (Toolbarbutton)comp.getFellow("btnNew");
//        btnNew.addEventListener("onClick", new EventListener<Event>() {
//            @Override
//            public void onEvent(Event event) throws Exception {
//                Messagebox.show("onNewClick");
//            }
//        });
    }

    private <K, V> Map<K, V> singleEntryMap(K key, V value) {
        Map<K, V> result = new HashMap<>();
        result.put(key, value);
        return result;
    }

    @Listen("onClick = #btnNew")
    public void newClick(Event e) {
        //create a window programmatically and use it as a modal dialog.
        Window window = (Window) Executions.createComponents(
                "/zul/tableForm.zul", null, singleEntryMap("action", "create"));
        window.doModal();
    }

    @Listen("onClick = #btnOpen")
    public void editClick(Event e) {
        //create a window programmatically and use it as a modal dialog.
        Window window = (Window) Executions.createComponents(
                "/zul/tableForm.zul", null, singleEntryMap("action", "edit"));
        //TODO get selected from grid
        window.doModal();
        window.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                //todo refresh me
                Clients.showNotification("Closed");
            }
        });
    }

    @Listen("onClick = #btnDelete")
    public void delClick(Event e) {
        // list all checked
    }

}
