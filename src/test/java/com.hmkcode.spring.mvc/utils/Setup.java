/*
package com.hmkcode.spring.mvc.utils;

import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Setup {
    private final static String PROPERTIES_FILE = "src/test/resources/test-config.properties";

    private String database;
    private String user;
    private String password;

    public Setup() {
        loadProperties();
    }

    private void loadProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fis);
            database = property.getProperty("database");
            user = property.getProperty("user");
            password = property.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Properties do not loaded. " + e.getCause());
        }
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setupData(DatabaseManager manager, String database) {
        this.database = database;
        try {
            manager.connect("", user, password);
        } catch (RuntimeException e) {
            throw new RuntimeException("For testing, change the name and password in a file 'test-config.properties'."
                    + "\n" + e.getCause());
        }
        manager.dropDatabase(database);
        manager.createDatabase(database);
        manager.connect(database, user, password);
        createTablesWithData(manager);
    }

    public void dropData(DatabaseManager manager) {
        try {
            manager.connect("", user, password);
            manager.dropDatabase(database);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createTablesWithData(DatabaseManager manager) {
        manager.createTable("users (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        manager.createTable("users2 (id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");

        Map<String, Object> dataSet = new LinkedHashMap<>();
        dataSet.put("name", "Vasia");
        dataSet.put("password", "****");
        dataSet.put("id", "22");
        manager.insert("users", dataSet);
    }
}
*/
