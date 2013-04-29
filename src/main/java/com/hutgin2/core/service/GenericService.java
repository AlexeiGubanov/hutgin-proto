package com.hutgin2.core.service;

import com.hutgin2.core.dao.search.ISearch;
import com.hutgin2.core.dao.search.SearchResult;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, ID extends Serializable> {
    T find(ID id);

    T[] find(ID... ids);

    boolean save(T entity);

    boolean[] save(T... entities);

    boolean remove(T entity);

    void remove(T... entities);

    boolean removeById(ID id);

    void removeByIds(ID... ids);

    List<T> findAll();

    <RT> List<RT> search(ISearch search);


    int count(ISearch search);

    <RT> SearchResult<RT> searchAndCount(ISearch search);

    void refresh(T... entities);

}
