package com.hutgin2.ui.zk;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;


@org.springframework.stereotype.Component("tableController")
@Scope("desktop")
public class TableFormController extends SelectorComposer<Component> {
    @Autowired
    private TableMetaService tableMetaService;

    private String action;

    @Wire
    Textbox name;
    @Wire
    Textbox tableName;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);    //To change body of overridden methods use File | Settings | File Templates.
        action = (String) Executions.getCurrent().getArg().get("action");
//        Clients.alert(action);
    }

    @Listen("onClick = #saveProfile")
    public void save(Event e) {
        if (action == "create") {
            TableMeta tm = new TableMeta();
            tm.setName(name.getValue());
            tm.setTableName(tableName.getValue());
            tableMetaService.save(tm);
            // TODO refresh me
        }
    }


}
