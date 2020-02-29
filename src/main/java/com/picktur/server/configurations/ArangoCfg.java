package com.picktur.server.configurations;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = { "com.picktur.server.repositories" })
//@EnableArangoAuditing(auditorAwareRef = "auditorProvider")
public class ArangoCfg extends AbstractArangoConfiguration {

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder().host("198.23.255.20", 8529).user("root").password("password");
    }

    @Override
    public String database() {
        return "Picktur";
    }

   /* @Bean
    public AuditorAware<User> auditorProvider() {
        return new AuditorProvider();
    }*/
}
