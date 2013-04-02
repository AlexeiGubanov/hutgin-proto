package com.hutgin2.dao;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.entity.Entity;

import java.io.Serializable;

public interface GeneralEntityDAO {

    public Entity find(TableMeta type, Serializable id);


}
