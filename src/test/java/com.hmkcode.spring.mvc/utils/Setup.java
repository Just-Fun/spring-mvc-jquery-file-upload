package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.DatabaseManager;
import com.hmkcode.spring.mvc.model.PostgreSQLManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Setup {
    private final static String PROPERTIES_FILE = "src/test/resources/test-config.properties";
    private final String FILE1 = "src/test/resources/file1.txt";
    private final String FILE2 = "src/test/resources/file2.txt";
    private final String FILE3 = "src/test/resources/file3.txt";
    private final String FILE4 = "src/test/resources/file4.txt";

    long sesson1 = 1476954435111L;
    long sesson2 = 1476954435222L;

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

        manager.dropDatabase(databaseForTests);
        manager.createDatabase(databaseForTests);
        manager.connect(databaseForTests, userName, password);
        createTablesWithData(manager);
    }

    public void dropData() {
        try {
            manager.connect("", userName, password);
//            manager.dropDatabase(databaseForTests);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createTablesWithData(DatabaseManager manager) {
        manager.createTable("files(id SERIAL PRIMARY KEY, name CHAR(50) NOT NULL, " +
                "file BYTEA, session BIGINT)");
        manager.createTable("results(id SERIAL PRIMARY KEY, session BIGINT, result BYTEA)");
        insertFiles();
    }

    private void insertFiles() {
        InputStream inputStream1 = getFile(FILE1);
        manager.insertFile("file1.txt", inputStream1, sesson1);
        InputStream inputStream2 = getFile(FILE2);
        manager.insertFile("file1.txt", inputStream2, sesson2);
        InputStream inputStream3 = getFile(FILE3);
        manager.insertFile("file1.txt", inputStream3, sesson2);
    }


    public InputStream getFile(String path) {
        File initialFile = new File(path);
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return targetStream;
    }

    public long getSesson1() {
        return sesson1;
    }

    public long getSesson2() {
        return sesson2;
    }
}
