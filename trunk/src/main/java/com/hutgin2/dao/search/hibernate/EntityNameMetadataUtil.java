package com.hutgin2.dao.search.hibernate;

import com.hutgin2.dao.search.Metadata;
import com.hutgin2.dao.search.MetadataUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of MetadataUtil for Hibernate
 * <p/>
 * A singleton instance of this class is maintained for each SessionFactory.
 * This should be accessed using
 * {@link EntityNameMetadataUtil#getInstanceForSessionFactory(SessionFactory)}.
 *
 * @author dwolverton
 */
public class EntityNameMetadataUtil implements MetadataUtil {

    private static Map<SessionFactory, EntityNameMetadataUtil> map = new HashMap<SessionFactory, EntityNameMetadataUtil>();

    public static EntityNameMetadataUtil getInstanceForSessionFactory(SessionFactory sessionFactory) {
        EntityNameMetadataUtil instance = map.get(sessionFactory);
        if (instance == null) {
            instance = new EntityNameMetadataUtil();
            instance.sessionFactory = sessionFactory;
            map.put(sessionFactory, instance);
        }
        return instance;
    }

    private SessionFactory sessionFactory;

    protected EntityNameMetadataUtil() {
    }

    // --- Public Methods ---

    public Serializable getId(String entityName, Object entity) {
        if (entity == null)
            throw new NullPointerException("Cannot get ID from null object.");
        return get(entityName).getIdValue(entity);
    }

    public boolean isId(String entityName, String propertyPath) {
        if (propertyPath == null || "".equals(propertyPath))
            return false;
        // with hibernate, "id" always refers to the id property, no matter what
        // that property is named. just make sure the segment before this "id"
        // refers to an entity since only entities have ids.
        if (propertyPath.equals("id")
                || (propertyPath.endsWith(".id") && get(entityName, propertyPath.substring(0, propertyPath.length() - 3))
                .isEntity()))
            return true;

        // see if the property is the identifier property of the entity it
        // belongs to.
        int pos = propertyPath.lastIndexOf(".");
        if (pos != -1) {
            Metadata parentType = get(entityName, propertyPath.substring(0, pos));
            if (!parentType.isEntity())
                return false;
            return propertyPath.substring(pos + 1).equals(parentType.getIdProperty());
        } else {
            return propertyPath.equals(sessionFactory.getClassMetadata(entityName).getIdentifierPropertyName());
        }
    }

    public Metadata get(String entityName) throws IllegalArgumentException {
        ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
        if (cm == null) {
            throw new IllegalArgumentException("Unable to introspect " + entityName + ". The entity is not a registered Hibernate entity.");
        } else {
            return new HibernateEntityMetadata(sessionFactory, cm, null);
        }
    }

    public Metadata get(String entityName, String propertyPath) throws IllegalArgumentException {
        try {
            Metadata md = get(entityName);
            if (propertyPath == null || "".equals(propertyPath))
                return md;

            String[] chain = propertyPath.split("\\.");

            for (int i = 0; i < chain.length; i++) {
                md = md.getPropertyType(chain[i]);
            }

            return md;

        } catch (HibernateException ex) {
            throw new IllegalArgumentException("Could not find property '" + propertyPath + "' on entity "
                    + entityName + ".");
        }
    }

}
