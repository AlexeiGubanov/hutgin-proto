package com.hutgin2.export.hbm;

import com.hutgin2.meta.DatabaseModel;
import com.hutgin2.meta.FieldMeta;
import com.hutgin2.meta.TableMeta;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertyElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class Exporter {

    public void export(DatabaseModel model, OutputStream outputStream) {
        JaxbHibernateMapping mapping = new JaxbHibernateMapping();

        List<Object> classes = mapping.getClazzOrSubclassOrJoinedSubclass();
        for (TableMeta table : model.getTables()) {
            JaxbHibernateMapping.JaxbClass e = new JaxbHibernateMapping.JaxbClass();
//            e.setName(table.getName());
            e.setEntityName(table.getName());
            e.setTable(table.getName());
            classes.add(e);
            List<Object> ep = e.getPropertyOrManyToOneOrOneToOne();
            for (FieldMeta field : table.getFields()) {
                JaxbPropertyElement p = new JaxbPropertyElement();
                p.setColumn(field.getName());
                p.setName(field.getName());
                p.setTypeAttribute(field.getType().getName());
                if (field.getSize() != null)
                    p.setLength(field.getSize().toString());
                ep.add(p);
            }
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JaxbHibernateMapping.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//            String docType = "<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">";
//            marshaller.setProperty("com.sun.xml.bind.xmlHeaders", xml + "\n" + docType);
            marshaller.marshal(mapping, outputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void export(DatabaseModel model, String fileName) throws FileNotFoundException {
        export(model, new FileOutputStream(fileName));

    }
}
