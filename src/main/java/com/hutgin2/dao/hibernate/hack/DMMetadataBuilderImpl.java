package com.hutgin2.dao.hibernate.hack;

import com.hutgin2.meta.DatabaseModel;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataBuilder;
import org.hibernate.metamodel.MetadataSourceProcessingOrder;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.config.spi.ConfigurationService;

import javax.persistence.SharedCacheMode;

/**
 * Kind of hack to support DatabaseModel
 */
public class DMMetadataBuilderImpl implements MetadataBuilder {
    private final MetadataSources sources;
    private final OptionsImpl options;
    private final DatabaseModel model;

    public DMMetadataBuilderImpl(MetadataSources sources, DatabaseModel model) {
        this.model = model;
        this.sources = sources;
        this.options = new OptionsImpl(sources.getServiceRegistry());
    }

    @Override
    public MetadataBuilder with(NamingStrategy namingStrategy) {
        this.options.namingStrategy = namingStrategy;
        return this;
    }

    @Override
    public MetadataBuilder with(MetadataSourceProcessingOrder metadataSourceProcessingOrder) {
        this.options.metadataSourceProcessingOrder = metadataSourceProcessingOrder;
        return this;
    }

    @Override
    public MetadataBuilder with(SharedCacheMode sharedCacheMode) {
        this.options.sharedCacheMode = sharedCacheMode;
        return this;
    }

    @Override
    public MetadataBuilder with(AccessType accessType) {
        this.options.defaultCacheAccessType = accessType;
        return this;
    }

    @Override
    public MetadataBuilder withNewIdentifierGeneratorsEnabled(boolean enabled) {
        this.options.useNewIdentifierGenerators = enabled;
        return this;
    }

    @Override
    public Metadata buildMetadata() {
        return new DMMetadataImpl(sources, options, model);
    }

    protected static class OptionsImpl implements Metadata.Options {
        private MetadataSourceProcessingOrder metadataSourceProcessingOrder = MetadataSourceProcessingOrder.HBM_FIRST;
        private NamingStrategy namingStrategy = EJB3NamingStrategy.INSTANCE;
        private SharedCacheMode sharedCacheMode = SharedCacheMode.ENABLE_SELECTIVE;
        private AccessType defaultCacheAccessType;
        private boolean useNewIdentifierGenerators;
        private boolean globallyQuotedIdentifiers;
        private String defaultSchemaName;
        private String defaultCatalogName;

        public OptionsImpl(ServiceRegistry serviceRegistry) {
            ConfigurationService configService = serviceRegistry.getService(ConfigurationService.class);

            // cache access type
            defaultCacheAccessType = configService.getSetting(
                    AvailableSettings.DEFAULT_CACHE_CONCURRENCY_STRATEGY,
                    new ConfigurationService.Converter<AccessType>() {
                        @Override
                        public AccessType convert(Object value) {
                            return AccessType.fromExternalName(value.toString());
                        }
                    }
            );

            useNewIdentifierGenerators = configService.getSetting(
                    AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS,
                    new ConfigurationService.Converter<Boolean>() {
                        @Override
                        public Boolean convert(Object value) {
                            return Boolean.parseBoolean(value.toString());
                        }
                    },
                    false
            );

            defaultSchemaName = configService.getSetting(
                    AvailableSettings.DEFAULT_SCHEMA,
                    new ConfigurationService.Converter<String>() {
                        @Override
                        public String convert(Object value) {
                            return value.toString();
                        }
                    },
                    null
            );

            defaultCatalogName = configService.getSetting(
                    AvailableSettings.DEFAULT_CATALOG,
                    new ConfigurationService.Converter<String>() {
                        @Override
                        public String convert(Object value) {
                            return value.toString();
                        }
                    },
                    null
            );

            globallyQuotedIdentifiers = configService.getSetting(
                    AvailableSettings.GLOBALLY_QUOTED_IDENTIFIERS,
                    new ConfigurationService.Converter<Boolean>() {
                        @Override
                        public Boolean convert(Object value) {
                            return Boolean.parseBoolean(value.toString());
                        }
                    },
                    false
            );
        }


        @Override
        public MetadataSourceProcessingOrder getMetadataSourceProcessingOrder() {
            return metadataSourceProcessingOrder;
        }

        @Override
        public NamingStrategy getNamingStrategy() {
            return namingStrategy;
        }

        @Override
        public AccessType getDefaultAccessType() {
            return defaultCacheAccessType;
        }

        @Override
        public SharedCacheMode getSharedCacheMode() {
            return sharedCacheMode;
        }

        @Override
        public boolean useNewIdentifierGenerators() {
            return useNewIdentifierGenerators;
        }

        @Override
        public boolean isGloballyQuotedIdentifiers() {
            return globallyQuotedIdentifiers;
        }

        @Override
        public String getDefaultSchemaName() {
            return defaultSchemaName;
        }

        @Override
        public String getDefaultCatalogName() {
            return defaultCatalogName;
        }
    }
}
