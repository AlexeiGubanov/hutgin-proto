package com.hutgin2.export.hbm;

import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertyElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class Exporter {

    public void export(List<TableMeta> tables, String fileName) {
        JaxbHibernateMapping mapping = new JaxbHibernateMapping();

        List<Object> classes = mapping.getClazzOrSubclassOrJoinedSubclass();
        for (TableMeta table : tables) {
            JaxbHibernateMapping.JaxbClass e = new JaxbHibernateMapping.JaxbClass();
            e.setName(table.getName());
            e.setEntityName(table.getName());
            classes.add(e);
            List<Object> ep = e.getPropertyOrManyToOneOrOneToOne();
            for (FieldMeta field : table.getFields()) {
                JaxbPropertyElement p = new JaxbPropertyElement();
                p.setColumn(field.getName());
                p.setName(field.getName());
                p.setTypeAttribute(field.getType().toString());
                if (field.getSize() != null)
                    p.setLength(field.getSize().toString());
                ep.add(p);
            }
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JaxbHibernateMapping.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(mapping, new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
