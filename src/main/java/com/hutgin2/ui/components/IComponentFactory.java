package com.hutgin2.ui.components;

import java.util.Map;

public interface IComponentFactory {

    Object createComponent(String path, Object parent, Map<String, Object> params);
}
