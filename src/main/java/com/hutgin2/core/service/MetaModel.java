package com.hutgin2.core.service;

import com.hutgin2.core.meta.DatabaseModel;

public interface MetaModel {

    DatabaseModel getModel();

    void refresh();

}
