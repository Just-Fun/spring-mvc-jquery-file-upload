package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.DatabaseManager;
import com.hmkcode.spring.mvc.model.PostgreSQLManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateDB {
    private final static String PROPERTIES_FILE = "src/test/resources/test-config.properties";

    private static String userName;
    private static String password;
    private static String database;

    static DatabaseManager manager;
    private Map<String, Integer> map;

    public DatabaseManager createData() {
        manager = new PostgreSQLManager();
        loadProperties();
        setupData();
        return manager;
    }

    private void loadProperties() {
        Utils utils = new Utils();
        userName = utils.getUserName();
        password = utils.getPassword();
        database = utils.getDatabase();
    }

    public void setupData() {
        manager.createDatabase(database);
        manager.connect("", userName, password);
        manager.connect(database, userName, password);
        createTablesWithData(manager);
    }

    public void createTablesWithData(DatabaseManager manager) {
        manager.createTable("files(id SERIAL PRIMARY KEY, name CHAR(50) NOT NULL, " +
                "file BYTEA, session BIGINT)");
        manager.createTable("results(id SERIAL PRIMARY KEY, session BIGINT, result BYTEA)");
    }
}
