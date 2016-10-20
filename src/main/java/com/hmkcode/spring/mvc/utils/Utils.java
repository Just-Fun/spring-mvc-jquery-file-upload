package com.hmkcode.spring.mvc.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Serzh on 10/19/16.
 */
public class Utils {

    private final String PROPERTIES_FILE = "src/main/resources/config.properties";
    private String host;
    private String port;

    private String database;
    private String userName;
    private String password;

    private String databaseForTests;

    public Utils() {
        loadProperties();
    }

    private void loadProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fis);
            host = property.getProperty("host");
            port = property.getProperty("port");
            database = property.getProperty("database");
            userName = property.getProperty("userName");
            password = property.getProperty("password");
            databaseForTests = property.getProperty("databaseForTests");
        } catch (IOException e) {
            throw new RuntimeException("Some trouble with loading properties: " + e.getCause());
        }
    }

    public  String getHost() {
        return host;
    }

    public  String getPort() {
        return port;
    }

    public  String getDatabase() {
        return database;
    }

    public  String getUserName() {
        return userName;
    }

    public  String getPassword() {
        return password;
    }

    public String getDatabaseForTests() {
        return databaseForTests;
    }
}
