package com.hutgin2.ui.zk.components.admin;

import com.hutgin2.core.dao.search.Search;
import com.hutgin2.core.dao.search.Sort;
import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.core.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.*;

import java.util.*;

@org.springframework.stereotype.Component("tablesController")
@Scope("desktop")
public class TablesController extends SelectorComposer<Component> {
    @Wire
    Grid tablesList;  // autowired
    @Wire
    Column column_check;
    @Wire
    Column column_name;
    @Wire
    Column column_tableName;
//    @Wire
//    Paging paging; // autowired

    private BindingListModelList<TableMeta> model;

    @Autowired
    private TableMetaService tableMetaService;

    @Listen("onCreate = #tablesList")
    public void onCreate$tablesList(Event event) {
//        paging.setPageSize(10);
//        paging.setPageIncrement(10);
//        paging.setDetailed(true);

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

        column_name.setSortDirection("natural");
        column_tableName.setSortDirection("natural");
        column_name.addEventListener("onSort", new ColumnSortListener());
        column_tableName.addEventListener("onSort", new ColumnSortListener());

        column_name.setSortAscending(new FieldComparator("name", true));
        column_name.setSortDescending(new FieldComparator("name", false));
        column_tableName.setSortAscending(new FieldComparator("tableName", true));
        column_tableName.setSortDescending(new FieldComparator("tableName", false));

    }

    private class ColumnSortListener implements EventListener<Event> {
        @Override
        public void onEvent(Event event) throws Exception {
            final Column col = (Column) event.getTarget();
            final String sortDirection = col.getSortDirection();
            col.setSortDirection(sortDirection);

            if ("ascending".equals(sortDirection)) {
                final Comparator<?> cmpr = col.getSortDescending();
                if (cmpr instanceof FieldComparator) {
                    String orderBy = ((FieldComparator) cmpr).getRawOrderBy();
                    Search search = new Search(TableMeta.class);
                    search.addSort(new Sort(orderBy, false));
                    List<TableMeta> tables = tableMetaService.search(search);
                    model = new BindingListModelList<>(tables, true); // todo provide mine  with paging and sorting
                    tablesList.setModel(model);
                    tablesList.invalidate();
                }
            } else if ("descending".equals(sortDirection) || "natural".equals(sortDirection) || Strings.isBlank(sortDirection)) {
                final Comparator<?> cmpr = col.getSortAscending();
                if (cmpr instanceof FieldComparator) {
                    String orderBy = ((FieldComparator) cmpr).getRawOrderBy();
                    Search search = new Search(TableMeta.class);
                    search.addSort(new Sort(orderBy, true));
                    List<TableMeta> tables = tableMetaService.search(search);
                    model = new BindingListModelList<>(tables, true); // todo provide mine  with paging and sorting
                    tablesList.setModel(model);
                    tablesList.invalidate();

                }
            }
//            event.stopPropagation();
//            ((WebAppCtrl)col.getPage().getDesktop().getWebApp()).getUiEngine().addSmartUpdate(col,"sortDirection", sortDirection, false);
//                        Clients.showNotification(col.getId() + sortDirection);
//                            final Comparator<?> cmpr = lh.getSortAscending();
//                            if (cmpr instanceof FieldComparator) {
//                                String orderBy = ((FieldComparator) cmpr).getOrderBy();
//                                orderBy = StringUtils.substringBefore(orderBy, "ASC").trim();
//
//                                // update SearchObject with orderBy
//                                getSearchObject().clearSorts();
//                                getSearchObject().addSort(orderBy, false);
//                            }
        }
    }

    // add sorting
//        tablesList.

    @Listen("onClick = #btnNew")
    public void onClick$btnNew(Event e) {
        Map<String, Object> params = new HashMap<>();
        params.put("action", "create");
        params.put("grid", tablesList);
        Window win = (Window) Executions.createComponents("/zul/components/admin/tableForm.zul", null, params);
        win.doModal();
    }


    @Listen("onClick = #refresh")
    public void onClick$refresh(Event e) {
        refresh(e);
    }

    private void refresh(Event e) {
        Events.postEvent("onCreate", this.tablesList, e);
        tablesList.invalidate();
    }

    @Listen("onClick = #btnDelete")
    public void onClick$btnDelete(Event e) {
        final Set<String> ids = new HashSet<>();
        Rows rows = this.tablesList.getRows();
        for (Component component : rows.getChildren()) {
            if (component instanceof Row) {
                Row row = (Row) component;
                TableMeta tm = (TableMeta) row.getAttribute("data");
                Checkbox cb = (Checkbox) row.getFellow("cb" + row.getId());
                if (cb.isChecked()) {
                    ids.add(tm.getName());
                }
            }
        }
        if (ids.size() > 0) {
            Messagebox.show("Are you sure to detele items " + ids.toString() + "?", "Delete confirmation", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
                @Override
                public void onEvent(Event event) throws Exception {
                    tableMetaService.removeByIds(ids.toArray(new String[ids.size()]));
                    List<TableMeta> tables = tableMetaService.findAll();
                    model = new BindingListModelList<>(tables, true); // todo provide mine  with paging and sorting
                    tablesList.setModel(model);
                }
            });
        }

    }

    public static interface EventListenerBuilder {
        EventListener getEventListener(TableMeta tableMeta);
    }


}
