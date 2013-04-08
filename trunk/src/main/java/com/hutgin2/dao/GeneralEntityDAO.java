package com.hutgin2.dao;

import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.dao.search.ExampleOptions;
import com.hutgin2.dao.search.Filter;
import com.hutgin2.dao.search.ISearch;
import com.hutgin2.entity.Entity;
import com.hutgin2.entity.EntityCollection;

import java.io.Serializable;

public interface GeneralEntityDAO {

    Entity find(TableMeta type, Serializable id);

    boolean save(Entity entity);

    boolean remove(Entity entity);

    EntityCollection findAll(TableMeta type);

    EntityCollection search(TableMeta type, ISearch search);

    Entity searchUnique(TableMeta type, ISearch search);

    int count(ISearch search);

    EntityCollection searchAndCount(TableMeta type, ISearch search);

    void refresh(Entity... entities);

    void flush();

    Filter getFilterFromExample(Entity example);

    public Filter getFilterFromExample(Entity example, ExampleOptions options);


}
