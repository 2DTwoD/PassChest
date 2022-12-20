package org.goznak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class PassChestApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassChestApplication.class, args);
    }
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5433/password_chest_db");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("1");
        return dataSourceBuilder.build();
    }
}
