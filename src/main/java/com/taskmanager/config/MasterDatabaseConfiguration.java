package com.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;

/**
 * @author harjeevanSingh
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.taskmanager.dao.master")
public class MasterDatabaseConfiguration {

    @Value("${spring.master.username}")
    private String username;

    @Value("${spring.master.password}")
    private String password;

    @Autowired
    @Bean(name = "masterJdbc")
    @Primary
    public JdbcTemplate getJdbcTemplate(@Qualifier("master") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Primary
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.master")
    public DataSource dataSource() {
        return DataSourceBuilder.create().username(username).password(password).build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("master") DataSource dataSource) {
        return builder
                .dataSource(dataSource).packages("com.taskmanager.entities")
                .persistenceUnit("master").build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
