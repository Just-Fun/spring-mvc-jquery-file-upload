package com.hmkcode.spring.mvc.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Serzh on 10/7/16.
 */
public class PostgreSQLManager implements DatabaseManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverException("Not installed PostgreSQL JDBC driver.", e);
        }
        loadProperties();
    }

    private static final String ERROR = "It is impossible because: ";
    private static final String PROPERTIES_FILE = "src/main/resources/config.properties";
    private static String host;
    private static String port;

    private static String database;
    private static String userName;
    private static String password;

    private Connection connection;
    private JdbcTemplate template;

    private static void loadProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fis);
            host = property.getProperty("host");
            port = property.getProperty("port");
            database = property.getProperty("database");
            userName = property.getProperty("userName");
            password = property.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Properties do not loaded. " + e.getCause());
        }
    }

    @Override
    public void connect() {
        closeOpenedConnection();
        getConnection();
    }

    @Override
    public Connection getConnection() {
        try {
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
            connection = DriverManager.getConnection(url, userName, password);
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        } catch (SQLException e) {
            connection = null;
            template = null;
            throw new DatabaseManagerException(
                    String.format("Cant get connection for db:%s user:%s", database, userName), e);
        }
        return connection;
    }

    private void closeOpenedConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                template = null;
            } catch (SQLException e) {
                throw new DatabaseManagerException(ERROR, e);
            }
        }
    }

/*The sample table is created as below to have a blob field to store file.
CREATE TABLE t1 (c1 INT PRIMARY KEY NOT NULL, c2 BLOB(5M));*/
    public void insertImage() {
        String query = "INSERT INTO t1 VALUES (?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, 100);
            File fBlob = new File("image1.gif");
            FileInputStream is = new FileInputStream(fBlob);
            pstmt.setBinaryStream(2, is, (int) fBlob.length());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void retrievingImage() {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM t1");
            while (rs.next()) {
                int val1 = rs.getInt(1);
                InputStream val2 = rs.getBinaryStream(2);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
