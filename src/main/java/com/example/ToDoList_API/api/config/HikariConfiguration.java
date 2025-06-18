package com.example.ToDoList_API.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class HikariConfiguration {

     @Bean
     public HikariConfig hikariConfig() {
          HikariConfig config = new HikariConfig();
          config.setPoolName("POOL_CUSTOMIZED");
          config.setMaximumPoolSize(10);
          config.setMinimumIdle(1);
          config.setConnectionTimeout(30000);
          config.setIdleTimeout(600000);
          config.setLeakDetectionThreshold(2000);
          config.setValidationTimeout(5000);

          // Configurações do banco de dados H2 em memória
          config.setJdbcUrl("jdbc:mysql://localhost:3306/todolist_db");
          config.setUsername("root");
          config.setPassword("");

          return config;
     }

     @Bean
     public DataSource dataSource(HikariConfig hikariConfig) {
          return new HikariDataSource(hikariConfig); // Cria o HikariDataSource com a configuração
     }


     @Bean
     public CommandLineRunner disableReferentialIntegrity(DataSource dataSource) {
          return args -> {
               try (Connection conn = dataSource.getConnection()) {
                    conn.createStatement().execute("SET FOREIGN_KEY_CHECKS = 0");
               } catch (SQLException e) {
                    e.printStackTrace();
               }
          };
     }

}
