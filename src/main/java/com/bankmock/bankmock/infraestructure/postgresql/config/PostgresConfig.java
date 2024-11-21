package com.bankmock.bankmock.infraestructure.postgresql.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class PostgresConfig {

    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/bank_mock");
        config.setUsername("root");
        config.setPassword("1234");

        return new HikariDataSource(config);
    }
}
