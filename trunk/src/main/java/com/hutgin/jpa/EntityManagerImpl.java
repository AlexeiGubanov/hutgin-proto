package com.hutgin.jpa;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;
import java.util.Map;

public class EntityManagerImpl implements EntityManager {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private Table meta;

    public EntityManagerImpl(Table meta, DataSource dataSource) {
        this.meta = meta;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void persist(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T merge(T t) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T find(Class<T> tClass, Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T find(Class<T> tClass, Object o, Map<String, Object> stringObjectMap) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T find(Class<T> tClass, Object o, LockModeType lockModeType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T find(Class<T> tClass, Object o, LockModeType lockModeType, Map<String, Object> stringObjectMap) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T getReference(Class<T> tClass, Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setFlushMode(FlushModeType flushModeType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FlushModeType getFlushMode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void lock(Object o, LockModeType lockModeType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> stringObjectMap) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(Object o, Map<String, Object> stringObjectMap) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> stringObjectMap) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void detach(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setProperty(String s, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Object> getProperties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Query createQuery(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> tCriteriaQuery) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> TypedQuery<T> createQuery(String s, Class<T> tClass) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Query createNamedQuery(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> tClass) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Query createNativeQuery(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Query createNativeQuery(String s, Class aClass) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Query createNativeQuery(String s, String s2) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void joinTransaction() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T unwrap(Class<T> tClass) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getDelegate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isOpen() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityTransaction getTransaction() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Metamodel getMetamodel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
