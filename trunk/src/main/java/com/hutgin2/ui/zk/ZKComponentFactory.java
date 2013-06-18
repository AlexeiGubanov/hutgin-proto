package com.hutgin2.ui.zk;

import com.hutgin2.ui.components.IComponentFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import java.util.Map;

//@org.springframework.stereotype.Component
public class ZKComponentFactory implements IComponentFactory {
    @Override
    public Object createComponent(String path, Object parent, Map<String, Object> params) {
        if (path.contains(".zul")) {
            return Executions.createComponents(path, (Component) parent, params);
        } else {
            // check type
            if (path.startsWith("/admin/tables")) {
                return Executions.createComponents("/zul/tablesGrid.zul", (Component) parent, params);
            }
        }
        return null;

    }
}
