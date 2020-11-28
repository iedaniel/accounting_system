package com.system.accounting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.system.accounting.service.repository",
        entityManagerFactoryRef = "accountingEntityManager",
        transactionManagerRef = "accountingTransactionManager"
)
@RequiredArgsConstructor
public class DBConfiguration {

    private final Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean accountingEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(accountingDataSource());
        em.setPackagesToScan("com.system.accounting.model.entity");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean
    public DataSource accountingDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("accounting.db.driver"));
        dataSource.setUrl(env.getProperty("accounting.db.url"));
        dataSource.setUsername(env.getProperty("accounting.db.user"));
        dataSource.setPassword(env.getProperty("accounting.db.pass"));
        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager accountingTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                accountingEntityManager().getObject());
        return transactionManager;
    }
}
