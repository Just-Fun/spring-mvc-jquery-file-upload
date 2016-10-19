package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.DatabaseManager;
import com.hmkcode.spring.mvc.model.PostgreSQLManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class Setup {
    private final static String PROPERTIES_FILE = "src/test/resources/test-config.properties";

    private static String userName;
    private static String password;
    private static String databaseForTests;

    DatabaseManager manager;

    public void createData() {
        manager = new PostgreSQLManager();
        loadProperties();
        setupData();
    }

    private void loadProperties() {
        Utils utils = new Utils();
        databaseForTests = utils.getDatabaseForTests();
        userName = utils.getUserName();
        password = utils.getPassword();
    }

    public void setupData() {

       /* try {
            manager.connect();
        } catch (RuntimeException e) {
            throw new RuntimeException("For testing, change the name and password in a file 'test-config.properties'."
                    + "\n" + e.getCause());
        }*/
        manager.dropDatabase(databaseForTests);
        manager.createDatabase(databaseForTests);
        manager.connect(databaseForTests, userName, password);
        createTablesWithData(manager);
    }

    public void dropData(DatabaseManager manager) {
        try {
            manager.connect("", userName, password);
//            manager.dropDatabase(databaseForTests);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createTablesWithData(DatabaseManager manager) {
        manager.createTable("users (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        manager.createTable("users2 (id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");
    }
}
