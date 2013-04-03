package com.hutgin2.dao.search.hibernate;

import com.hutgin2.dao.search.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * <p/>
 * Hibernate implementation of SearchFacade.
 * <p/>
 * <p/>
 * The SessionFactory must be set before an instance of this class can be used.
 * The <code>getCurrentSession()</code> method of the SessionFactory is used
 * when a session is needed.
 * <p/>
 * <p>To change this default behavior, you can override the protected {@link #getSession()} method.
 *
 * @author dwolverton
 */
public class HibernateSearchFacade implements SearchFacade {
    private SessionFactory sessionFactory;
    private EntityNameSearchProcessor processor;

    public HibernateSearchFacade() {
    }

    public HibernateSearchFacade(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.processor = EntityNameSearchProcessor.getInstanceForSessionFactory(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected EntityNameSearchProcessor getProcessor() {
        return processor;
    }

    @SuppressWarnings("unchecked")
    public List search(ISearch search) {
        return processor.search(getSession(), search);
    }

    @SuppressWarnings("unchecked")
    public List search(String entityName, ISearch search) {
        return processor.search(getSession(), entityName, search);
    }

    public int count(ISearch search) {
        return processor.count(getSession(), search);
    }

    public int count(String entityName, ISearch search) {
        return processor.count(getSession(), entityName, search);
    }

    @SuppressWarnings("unchecked")
    public SearchResult searchAndCount(ISearch search) {
        return processor.searchAndCount(getSession(), search);
    }

    @SuppressWarnings("unchecked")
    public SearchResult searchAndCount(String entityName, ISearch search) {
        return processor.searchAndCount(getSession(), entityName, search);
    }

    public Object searchUnique(ISearch search) {
        return processor.searchUnique(getSession(), search);
    }

    public Object searchUnique(String entityName, ISearch search) {
        return processor.searchUnique(getSession(), entityName, search);
    }

    public Filter getFilterFromExample(String entityName, Object example) {
        return processor.getFilterFromExample(entityName, example);
    }

    public Filter getFilterFromExample(String entityName, Object example, ExampleOptions options) {
        return processor.getFilterFromExample(entityName, example, options);
    }
}
