package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.util.Map;


@org.springframework.stereotype.Component("tableController")
@Scope("desktop")
public class TableFormController extends SelectorComposer<Component> {
    @Autowired
    private TableMetaService tableMetaService;


    private String action;
    private TableMeta entity;
    private Grid grid;

    @Wire
    Textbox name;      // autowired
    @Wire
    Textbox tableName; // autowired

    @Wire
    Window tableFormWindow;

    @Listen("onClose = #tableFormWindow")
    public void onClose$tableFormWindow(Event event) throws Exception {
        tableFormWindow.detach();
    }

    @Listen("onCreate = #tableFormWindow")
    public void onCreate$tableFormWindow(Event event) {
        final CreateEvent ce = (CreateEvent) event;
        Map<String, Object> args = (Map<String, Object>) ce.getArg();
        action = (String) args.get("action");
        entity = (TableMeta) args.get("entity");
        grid = (Grid) args.get("grid");
        if (entity != null) {
            name.setValue(entity.getName());
            tableName.setValue(entity.getTableName());
        }
        if ("edit".equals(action)) {
            name.setReadonly(true);
        }
    }

    @Listen("onClick = #save")
    public void onClick$save(Event e) {
        ListModelList model = (ListModelList) grid.getListModel();
        if ("create".equals(action)) {
            entity = new TableMeta();
            entity.setName(name.getValue());
            entity.setTableName(tableName.getValue());
            tableMetaService.save(entity);
            model.add(entity);
            grid.invalidate();//refresh
            action = "edit";
            name.setReadonly(true);
        }
        if ("edit".equals(action)) {
            entity.setName(name.getValue());
            entity.setTableName(tableName.getValue());
            tableMetaService.save(entity);
            grid.setModel(model);//refresh
            name.setReadonly(true);
        }

    }

    @Listen("onClick = #new")
    public void onClick$new(Event e) {
        action = "create";
        entity = null;
        name.setReadonly(false);
        Constraint c = name.getConstraint();
        name.setConstraint((Constraint) null);
        name.setValue("");
        name.setConstraint(c);
        tableName.setValue("");
        name.setFocus(true);
    }

    @Listen("onClick = #delete")
    public void onClick$delete(Event e) {
        if ("edit".equals(action)) {
            Messagebox.show("Are you sure to detele item " + entity.getName() + "?", "Delete confirmation", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
                @Override
                public void onEvent(Event event) throws Exception {
                    tableMetaService.removeById(entity.getName());
                    Events.postEvent("onCreate", grid, event);
                    tableFormWindow.detach();
                }
            });
        }
    }

    @Listen("onClick = #reload")
    public void onClick$reload(Event e) {
        if ("edit".equals(action)) {
            if (entity != null) {
                name.setValue(entity.getName());
                tableName.setValue(entity.getTableName());
            }
        } else {
            name.setValue(null);
            tableName.setValue(null);
        }
    }


}
