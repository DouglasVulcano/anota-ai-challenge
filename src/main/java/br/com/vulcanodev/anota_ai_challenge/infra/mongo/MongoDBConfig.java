package br.com.vulcanodev.anota_ai_challenge.infra.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MongoDBConfig {
    
    @Value("${spring.data.mongodb.uri}") 
    private String databaseUrl;

    @Bean
    public MongoDatabaseFactory mongoConfig() {
        return new SimpleMongoClientDatabaseFactory(databaseUrl);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(this.mongoConfig());
    }
}
