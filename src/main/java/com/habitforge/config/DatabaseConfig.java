package com.habitforge.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.habitforge.exceptions.DbPropertiesNotFound;

public class DatabaseConfig {

    private DatabaseConfig() {
    }

    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/com/habitforge/db.properties")) {

            if (input == null) {
                throw new DbPropertiesNotFound("Database properties file not found");
            }
            System.out.println("test loading properties");
            properties.load(input);
            System.out.println("test loading properties done");
        } catch (Exception e) {
            System.out.println("test loading properties error");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));
    }
}
