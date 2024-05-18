package com.example.db;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataBase {
    private static final DataBase INSTANCE = new DataBase();

    private static final String URL = "jdbc:postgresql://localhost:5432/db9";
    private static final String USER = "postgres";
    private static final String PASSWORD = "3272A567";
    private Connection connection;

    private DataBase(){
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            flywayMigration();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public static DataBase getInstance(){
        return INSTANCE;
    }
    public Connection getConnection(){
        return connection;
    }

    private void flywayMigration(){
        Flyway flyway = Flyway.configure().dataSource(URL,USER,PASSWORD).load();
        flyway.migrate();
    }

}
