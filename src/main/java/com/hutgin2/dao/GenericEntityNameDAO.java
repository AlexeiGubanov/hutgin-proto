package com.hutgin2.dao;

import com.hutgin2.dao.search.ExampleOptions;
import com.hutgin2.dao.search.Filter;
import com.hutgin2.dao.search.ISearch;
import com.hutgin2.dao.search.SearchResult;

import java.io.Serializable;
import java.util.List;

public interface GenericEntityNameDAO<T> {
    /**
     * <p/>
     * Get the entity with the specified type and id from the datastore.
     * <p/>
     * <p/>
     * If none is found, return null.
     */
    public T find(String entityName, Serializable id);

    /**
     * Get all entities of the specified type from the datastore that have one
     * of these ids. An array of entities is returned that matches the same
     * order of the ids listed in the call. For each entity that is not found in
     * the datastore, a null will be inserted in its place in the return array.
     */
    public T[] find(String entityName, Serializable... ids);

    /**
     * <p/>
     * Get a reference to the entity with the specified type and id from the
     * datastore.
     * <p/>
     * <p/>
     * This does not require a call to the datastore and does not populate any
     * of the entity's values. Values may be fetched lazily at a later time.
     * This increases performance if a another entity is being saved that should
     * reference this entity but the values of this entity are not needed.
     */
    public T getReference(String entityName, Serializable id);

    /**
     * <p/>
     * Get a reference to the entities of the specified type with the given ids
     * from the datastore. An array of entities is returned that matches the
     * same order of the ids listed in the call.
     * <p/>
     * <p/>
     * This does not require a call to the datastore and does not populate any
     * of the entities' values. Values may be fetched lazily at a later time.
     * This increases performance if a another entity is being saved that should
     * reference these entities but the values of these entities are not needed.
     */
    public T[] getReferences(String entityName, Serializable... ids);

    /**
     * <p/>
     * If the id of the entity is null or zero, add it to the datastore and
     * assign it an id; otherwise, update the corresponding entity in the
     * datastore with the properties of this entity. In either case the entity
     * passed to this method will be attached to the session.
     * <p/>
     * <p/>
     * If an entity to update is already attached to the session, this method
     * will have no effect. If an entity to update has the same id as another
     * instance already attached to the session, an error will be thrown.
     *
     * @return <code>true</code> if create; <code>false</code> if update.
     */
    public boolean save(String entityName, T entity);

    /**
     * <p/>
     * For each entity, if the id of the entity is null or zero, add it to the
     * datastore and assign it an id; otherwise, update the corresponding entity
     * in the datastore with the properties of this entity. In either case the
     * entity passed to this method will be attached to the session.
     * <p/>
     * <p/>
     * If an entity to update is already attached to the session, this method
     * will have no effect. If an entity to update has the same id as another
     * instance already attached to the session, an error will be thrown.
     */
    public boolean[] save(String entityName, T... entities);

    /**
     * Remove the specified entity from the datastore.
     *
     * @return <code>true</code> if the entity is found in the datastore and
     *         removed, <code>false</code> if it is not found.
     */
    public boolean remove(String entityName, T entity);

    /**
     * Remove all of the specified entities from the datastore.
     */
    public void remove(String entityName, T... entities);

    /**
     * Remove the entity with the specified type and id from the datastore.
     *
     * @return <code>true</code> if the entity is found in the datastore and
     *         removed, <code>false</code> if it is not found.
     */
    public boolean removeById(String entityName, Serializable id);

    /**
     * Remove all the entities of the given type from the datastore that have
     * one of these ids.
     */
    public void removeByIds(String entityName, Serializable... ids);

    /**
     * Get a list of all the objects of the specified type.
     */
    public List<T> findAll(String entityName);

    /**
     * Search for objects given the search parameters in the specified
     * <code>ISearch</code> object.
     */
    @SuppressWarnings("unchecked")
    public List<T> search(ISearch search);

    /**
     * Search for a single result using the given parameters.
     */
    public T searchUnique(ISearch search);

    /**
     * Returns the total number of results that would be returned using the
     * given <code>ISearch</code> if there were no paging or maxResults limits.
     */
    public int count(ISearch search);

    /**
     * Returns a <code>SearchResult</code> object that includes both the list of
     * results like <code>search()</code> and the total length like
     * <code>count()</code>.
     */
    @SuppressWarnings("unchecked")
    public SearchResult searchAndCount(ISearch search);

    /**
     * Returns <code>true</code> if the object is connected to the current
     * Hibernate session.
     */
    public boolean isAttached(String entityName, T entity);

    /**
     * Refresh the content of the given entity from the current datastore state.
     */
    public void refresh(String entityName, T... entities);

    /**
     * Flushes changes in the Hibernate session to the datastore.
     */
    public void flush();

    /**
     * Generates a search filter from the given example using default options.
     */
    public Filter getFilterFromExample(String entityName, T example);

    /**
     * Generates a search filter from the given example using the specified options.
     */
    public Filter getFilterFromExample(String entityName, T example, ExampleOptions options);

}
