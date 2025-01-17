package org.romanzhula.bookstoreorderservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @DependsOn("consulConfig") // ensures that ConsulConfig is initialized first
    public DataSource dataSource(ConsulConfig consulConfig) {
        // get data for DataSource from Consul
        String dbUrl = consulConfig.getDbUrl();
        String dbUsername = consulConfig.getDbUsername();
        String dbPassword = consulConfig.getDbPassword();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

}
