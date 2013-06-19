package com.hutgin2.ui.zk.components.admin;

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
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Component("tablesController")
@Scope("desktop")
public class TablesController extends SelectorComposer<Component> {

    @Wire
    Listbox listbox;

    @Wire
    Grid tablesList;

    @Wire
    Paging paging; // autowired

    private ListModelList<TableMeta> model;

    @Autowired
    private TableMetaService tableMetaService;

    private <K, V> Map<K, V> singleEntryMap(K key, V value) {
        Map<K, V> result = new HashMap<>();
        result.put(key, value);
        return result;
    }

    @Listen("onCreate = #tablesList")
    public void onCreate$tablesList(Event event) {

        paging.setPageSize(10);
        paging.setPageIncrement(10);
        paging.setDetailed(true);

        model = new ListModelList<>(); // todo provide mine  with paging and sorting
        tablesList.setRowRenderer(new TableMetaRowRenderer(tablesList));


        List<TableMeta> tables = tableMetaService.findAll();
        model.addAll(tables);
        tablesList.setModel(model);
    }

    @Listen("onCreate = #listbox")
    public void onCreate$listbox(Event event) {

        paging.setPageSize(10);
        paging.setPageIncrement(10);
        paging.setDetailed(true);

        // TODO supply paging
        listbox.setItemRenderer(new TableMetaItemRenderer());

        ListModelList<TableMeta> lml = new ListModelList<>(); // todo provide mine
        List<TableMeta> tables = tableMetaService.findAll();
        lml.addAll(tables);
        listbox.setModel(lml);
    }

    @Listen("onSelect = #listbox")
    public void onSelect$listbox(Event event) {
        Listitem item = listbox.getSelectedItem();

//        if (item != null) {
//            // CAST AND STORE THE SELECTED OBJECT
//            Order order = (Order) item.getAttribute("data");
//
//            if (order != null) {
//                // Set the ListModel and the itemRenderer for the order
//                // articles.g
//
//                HibernateSearchObject<Orderposition> soOrderPosition = new HibernateSearchObject<Orderposition>(Orderposition.class, getPageSizeOrderPositions());
//                soOrderPosition.addFilter(new Filter("order", order, Filter.OP_EQUAL));
//                // deeper loading of the relation to prevent the lazy loading
//                // problem.
//                soOrderPosition.addFetch("article");
//
//                // Set the ListModel.
//                getPlwOrderpositions().init(soOrderPosition, listBoxOrderArticle, paging_OrderArticleList);
//
//                // +++ get the SUM of the orderpositions +++ //
//                String s = String.valueOf(getOrderService().getOrderSum(order));
//                if (s != "null") {
//                    listfooter_OrderPosList_WholePrice.setLabel(String.valueOf(getOrderService().getOrderSum(order)));
//                } else
//                    listfooter_OrderPosList_WholePrice.setLabel("0.00");
//
//            }
//        }
    }


    public void onDoubleClickedItem(Event event) throws Exception {

        // get the selected object
        Listitem item = listbox.getSelectedItem();
//
//        if (item != null) {
//            // CAST AND STORE THE SELECTED OBJECT
//            Order anOrder = (Order) item.getAttribute("data");
//
//            showDetailView(anOrder);
//
//        }
    }

    @Listen("onClick = #btnNew")
    public void newClick(Event e) {
        Map<String, Object> params = new HashMap<>();
        params.put("action", "create");
        params.put("grid", tablesList);
        Window window = (Window) Executions.createComponents(
                "/zul/components/admin/tableForm.zul", null, params);
        window.doModal();
    }


    @Listen("onClick = #btnDelete")
    public void delClick(Event e) {
        // list all checked
    }

}
