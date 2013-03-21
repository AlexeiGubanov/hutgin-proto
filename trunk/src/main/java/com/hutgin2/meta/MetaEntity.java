package com.hutgin2.meta;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

@MappedSuperclass
public class MetaEntity {

    /**
     * Flag if entity itself (as a database record) is persistent.
     * User to add any fantom objects into memory model
     */
    private boolean persistable = true;

    @Transient
    public boolean isPersistable() {
        return persistable;
    }

    public void setPersistable(boolean persistable) {
        this.persistable = persistable;
    }

    private String properties;
    private Properties propertiesMap;

    @Column(length = 4096)
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Transient
    public Properties getPropertiesMap() {
        if (propertiesMap == null) {
            propertiesMap = new Properties();
            try {
                if (getProperties() != null)
                    propertiesMap.load(new StringReader(getProperties()));
            } catch (IOException e) {
                throw new IllegalStateException("Properties for " + this.toString() + " are invalid");
            }
        }
        return propertiesMap;
    }

    @Transient
    public String getProperty(String name) {
        return getPropertiesMap().getProperty(name);
    }

    public void setProperty(String name, String value) {
        getPropertiesMap().setProperty(name, value);
        // TODO serialize propertiesMap into properties field within service layer onupdate
    }


    private String description;

    @Column(length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
