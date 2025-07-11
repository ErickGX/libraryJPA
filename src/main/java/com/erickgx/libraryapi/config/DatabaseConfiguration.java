//package com.erickgx.libraryapi.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfiguration {
//
//    @Value("${spring.datasource.url}")
//    String url;
//    @Value("${spring.datasource.username}")
//    String username;
//    @Value("${spring.datasource.password}")
//    String password;
//    @Value("${spring.datasource.driver-class-name}")
//    String driver;
//
//
//    //implementação basica de datasource , nao recomendada o uso em prod
//    //nao suporta varias conexoes
////    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//            ds.setUrl(url);
//            ds.setUsername(username);
//            ds.setPassword(password);
//            ds.setDriverClassName(driver);
//
//        return ds;
//    }
//
//
//
//    @Bean
//    public DataSource hikariDataSource(){
//
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(username);
//        config.setPassword(password);
//        config.setDriverClassName(driver);
//
//        config.setMaximumPoolSize(10); //maximo de conexoes liberadas
//        config.setMinimumIdle(1); //minimo de conexoes inicial do pool
//        config.setPoolName("Library-db-pool");
//        config.setMaxLifetime(600000); //tempo em milisegundos 10 minutos
//        config.setConnectionTimeout(100000); //timeout para conseguir uma conexao
//        config.setConnectionTestQuery("select 1");
//
//        return new HikariDataSource(config);
//    }
//}
