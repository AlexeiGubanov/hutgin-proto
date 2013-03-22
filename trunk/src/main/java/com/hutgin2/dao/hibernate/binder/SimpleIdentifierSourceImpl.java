package com.hutgin2.dao.hibernate.binder;

import com.hutgin2.meta.FieldMeta;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.metamodel.binding.IdGenerator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.metamodel.source.binder.SimpleIdentifierSource;
import org.hibernate.metamodel.source.binder.SingularAttributeSource;

import java.util.HashMap;

public class SimpleIdentifierSourceImpl implements SimpleIdentifierSource {

    private FieldMeta fieldMeta;
    private MetadataImplementor metadata;

    public SimpleIdentifierSourceImpl(FieldMeta fieldMeta, MetadataImplementor metadata) {
        this.fieldMeta = fieldMeta;
        this.metadata = metadata;
    }

    @Override
    public SingularAttributeSource getIdentifierAttributeSource() {
        return new SingularAttributeSourceImpl(this.fieldMeta);
    }

    @Override
    public IdGenerator getIdentifierGeneratorDescriptor() {
        switch (fieldMeta.getInsertGenerationStrategy()) {
            case NONE:
            case DATABASE:
                return null;
            case DEFAULT_VALUE:
                throw new NotYetImplementedException("ID generation by default value is not yet implemented");
            case CLASS:
                String generatorName = fieldMeta.getInsertGenerator();
                // FIXME currently generatorName is hibernate generator strategy name!     org.hibernate.id.IdentifierGenerato
                // TODO hibernate generator may be provided only by its name, to resolve it use .getMetadataImplementor().getIdGenerator( generatorName );
                // TODO wrap user provided generators with IdGenerator
                // TODO check way of IdGenerator usage
                IdGenerator idGenerator = metadata.getIdGenerator(generatorName);
                if (idGenerator == null) {
                    idGenerator = new IdGenerator(fieldMeta.getTableName() + generatorName, generatorName, new HashMap<String, String>());
                    //Helper.extractParameters( entityElement().getId().getGenerator().getParam()
                }
                return idGenerator;
            case STORED_PROCEDURE:
                throw new NotYetImplementedException("ID generation by stored procedure value is not yet implemented");
        }
        return null;
    }

    @Override
    public Nature getNature() {
        return Nature.SIMPLE;
    }
}
