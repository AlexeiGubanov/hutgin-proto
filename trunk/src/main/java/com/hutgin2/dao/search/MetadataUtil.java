package com.hutgin2.dao.search;

import java.io.Serializable;

/**
 * The framework uses an implementation of this interface to introspect the
 * objects and relationships maintained by the JPA provider.
 * <p/>
 * This interface provides a layer of abstraction between the framework and the
 * underlying ORM or JPA provider (ex. Hibernate). By switching out the
 * implementation of this interface, the framework should be able to be used
 * with different JPA providers.
 *
 * @author dwolverton
 */
public interface MetadataUtil {
    /**
     * Get the value of the ID property of an entity.
     */
    public Serializable getId(String entityName, Object object);

    /**
     * Return true if the property at the given property path is the id of some
     * entity.
     */
    public boolean isId(String entityName, String propertyPath);

    /**
     * Get the Metadata for an entity class.
     *
     * @throws IllegalArgumentException if the class is not a Hibernate entity.
     */
    public Metadata get(String entityName) throws IllegalArgumentException;

    /**
     * Get the Metadata for a property of an entity class. The property can be
     * simple ("name") or nested ("organization.name").
     *
     * @throws IllegalArgumentException if the root class is not a Hibernate entity.
     * @throws IllegalArgumentException if the class does not have the given property.
     */
    public Metadata get(String entityName, String propertyPath) throws IllegalArgumentException;

}
