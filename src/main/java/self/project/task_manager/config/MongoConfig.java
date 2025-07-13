package self.project.task_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
@EnableMongoRepositories
public class MongoConfig {

    @Bean
    public MongoTransactionManager transactionManager(final MongoDatabaseFactory databaseFactory) {
        return new MongoTransactionManager(databaseFactory);
    }

    @Bean
    public ValidatingEntityCallback validatingEntityCallback(
            final LocalValidatorFactoryBean factory) {
        return new ValidatingEntityCallback(factory);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context, MongoCustomConversions conversions) {
        MappingMongoConverter converter = new MappingMongoConverter(factory, context);
        converter.setCustomConversions(conversions);
        // Remove _class field
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory, MappingMongoConverter converter) {
        return new MongoTemplate(factory, converter);
    }

}
