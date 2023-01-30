package org.goznak;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class PassChestApplication {
    @Value("${spring.jpa.database-platform}")
    private String springJpaDatabasePlatform;
    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;
    @Value("${spring.datasource.username}")
    private String springDatasourceUsername;
    @Value("${spring.datasource.password}")
    private String springDatasourcePassword;
    final public static String[] INFINITY_USERS = {"Демьяненко Д.С.", "Гость"};
    public static void main(String[] args) {
        SpringApplication.run(PassChestApplication.class, args);
    }
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(springJpaDatabasePlatform);
        dataSourceBuilder.url(springDatasourceUrl);
        dataSourceBuilder.username(springDatasourceUsername);
        dataSourceBuilder.password(springDatasourcePassword);
        return dataSourceBuilder.build();
    }
}
