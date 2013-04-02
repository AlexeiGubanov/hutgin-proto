package com.hutgin2.service;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.entity.Entity;

import java.io.Serializable;

public interface GeneralEntityService {

    Entity find(TableMeta type, Serializable id);
}
