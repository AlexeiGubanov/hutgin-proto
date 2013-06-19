package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import java.util.Map;


@org.springframework.stereotype.Component("tableController")
@Scope("desktop")
public class TableFormController extends GenericForwardComposer<Component> {
    @Autowired
    private TableMetaService tableMetaService;

    private String action;
    private TableMeta entity;
    private Grid grid;

    @Wire
    Textbox name;
    @Wire
    Textbox tableName;


    @Listen("onCreate = #tableForm")
    public void onCreate$tableForm(Event event) {
        final CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
        Map<String, Object> args = (Map<String, Object>) ce.getArg();
        action = (String) args.get("action");
        entity = (TableMeta) args.get("entity");
        grid = (Grid) args.get("grid");
        if (entity != null) {
            name.setValue(entity.getName());
            tableName.setValue(entity.getTableName());
        }
    }

    @Listen("onClick = #saveProfile")
    public void onClick$saveProfile(Event e) {
        ListModelList model = (ListModelList) grid.getModel();
        if ("create".equals(action)) {
            entity = new TableMeta();
            entity.setName(name.getValue());
            entity.setTableName(tableName.getValue());
            tableMetaService.save(entity);
            model.add(entity);
        }
        if ("edit".equals(action)) {
            entity.setName(name.getValue());
            entity.setTableName(tableName.getValue());
            tableMetaService.save(entity);
        }
        grid.setModel(model); // refresh
    }


}
