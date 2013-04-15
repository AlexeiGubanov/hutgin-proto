package com.hutgin2.ui.zk;

import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class RootRichlet extends GenericRichlet {

    @Override
    public void service(Page page) throws Exception {
        page.setTitle("New app");
        final Window w = new Window("Richlet Test", "normal", false);
        new Label("Hello World!").setParent(w);
        final Label l = new Label();
        l.setParent(w);

        final Button b = new Button("Change");
        b.addEventListener(Events.ON_CLICK,
                new EventListener() {
                    int count;

                    public void onEvent(Event evt) {
                        l.setValue("" + ++count);
                    }
                });
        b.setParent(w);

        w.setPage(page);
    }
}
