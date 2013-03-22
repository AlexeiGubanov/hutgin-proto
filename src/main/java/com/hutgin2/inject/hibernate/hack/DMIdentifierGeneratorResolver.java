package com.hutgin2.inject.hibernate.hack;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.cfg.ObjectNameNormalizer;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.config.spi.ConfigurationService;

import java.io.Serializable;
import java.util.Properties;

/**
 * Kind of hack
 * {@link org.hibernate.metamodel.source.internal.IdentifierGeneratorResolver}
 * TODO review
 */
public class DMIdentifierGeneratorResolver {
    private final MetadataImplementor metadata;

    DMIdentifierGeneratorResolver(MetadataImplementor metadata) {
        this.metadata = metadata;
    }

    // IdentifierGeneratorResolver.resolve() must execute after AttributeTypeResolver.resolve()
    // to ensure that identifier type is resolved.
    @SuppressWarnings({"unchecked"})
    void resolve() {
        for (EntityBinding entityBinding : metadata.getEntityBindings()) {
            if (entityBinding.isRoot()) {
                Properties properties = new Properties();
                properties.putAll(
                        metadata.getServiceRegistry()
                                .getService(ConfigurationService.class)
                                .getSettings()
                );
                //TODO: where should these be added???
                if (!properties.contains(AvailableSettings.PREFER_POOLED_VALUES_LO)) {
                    properties.put(AvailableSettings.PREFER_POOLED_VALUES_LO, "false");
                }
                if (!properties.contains(PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER)) {
                    properties.put(
                            PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER,
                            new ObjectNameNormalizerImpl(metadata)
                    );
                }
                entityBinding.getHierarchyDetails().getEntityIdentifier().createIdentifierGenerator(
                        metadata.getIdentifierGeneratorFactory(),
                        properties
                );
            }
        }
    }

    private static class ObjectNameNormalizerImpl extends ObjectNameNormalizer implements Serializable {
        private final boolean useQuotedIdentifiersGlobally;
        private final NamingStrategy namingStrategy;

        private ObjectNameNormalizerImpl(MetadataImplementor metadata) {
            this.useQuotedIdentifiersGlobally = metadata.isGloballyQuotedIdentifiers();
            this.namingStrategy = metadata.getNamingStrategy();
        }

        @Override
        protected boolean isUseQuotedIdentifiersGlobally() {
            return useQuotedIdentifiersGlobally;
        }

        @Override
        protected NamingStrategy getNamingStrategy() {
            return namingStrategy;
        }
    }
}
