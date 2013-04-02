package com.hutgin2.dao.hibernate;

import com.hutgin2.core.dao.search.Metadata;
import com.hutgin2.core.dao.search.hibernate.HibernateEntityMetadata;
import com.hutgin2.core.meta.TableMeta;
import com.hutgin2.entity.Entity;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class EntityMetadataUtil  /*implements MetadataUtil */ {

    private static Map<SessionFactory, EntityMetadataUtil> map = new HashMap<SessionFactory, EntityMetadataUtil>();

    private TableMeta tableMeta;

    public static EntityMetadataUtil getInstanceForSessionFactory(SessionFactory sessionFactory, TableMeta tableMeta) {
        EntityMetadataUtil instance = map.get(sessionFactory);
        if (instance == null) {
            instance = new EntityMetadataUtil();
            instance.sessionFactory = sessionFactory;
            instance.tableMeta = tableMeta;
            map.put(sessionFactory, instance);
        }
        return instance;
    }

    private SessionFactory sessionFactory;

    protected EntityMetadataUtil() {
    }

    // --- Public Methods ---

    public Serializable getId(Entity entity) {
        if (entity == null)
            throw new NullPointerException("Cannot get ID from null object.");
        return get(entity.getMeta()).getIdValue(entity);
    }

    public boolean isId(TableMeta tableMeta, String propertyPath) {
        if (propertyPath == null || "".equals(propertyPath))
            return false;
        // with hibernate, "id" always refers to the id property, no matter what
        // that property is named. just make sure the segment before this "id"
        // refers to an entity since only entities have ids.
        if (propertyPath.equals("id")
                || (propertyPath.endsWith(".id") && get(tableMeta, propertyPath.substring(0, propertyPath.length() - 3))
                .isEntity()))
            return true;

        // see if the property is the identifier property of the entity it
        // belongs to.
        int pos = propertyPath.lastIndexOf(".");
        if (pos != -1) {
            Metadata parentType = get(tableMeta, propertyPath.substring(0, pos));
            if (!parentType.isEntity())
                return false;
            return propertyPath.substring(pos + 1).equals(parentType.getIdProperty());
        } else {
            return propertyPath.equals(sessionFactory.getClassMetadata(tableMeta.getName()).getIdentifierPropertyName());
        }
    }

    public Metadata get(TableMeta tableMeta) throws IllegalArgumentException {
        ClassMetadata cm = sessionFactory.getClassMetadata(tableMeta.getName());
        if (cm == null) {
            throw new IllegalArgumentException("Unable to introspect " + tableMeta.getName().toString()
                    + ". The class is not a registered Hibernate entity.");
        } else {
            return new HibernateEntityMetadata(sessionFactory, cm, null);
        }
    }

    public Metadata get(TableMeta tableMeta, String propertyPath) throws IllegalArgumentException {
        try {
            Metadata md = get(tableMeta);
            if (propertyPath == null || "".equals(propertyPath))
                return md;

            String[] chain = propertyPath.split("\\.");

            for (int i = 0; i < chain.length; i++) {
                md = md.getPropertyType(chain[i]);
            }

            return md;

        } catch (HibernateException ex) {
            throw new IllegalArgumentException("Could not find property '" + propertyPath + "' on class "
                    + tableMeta.getName() + ".");
        }
    }


}
