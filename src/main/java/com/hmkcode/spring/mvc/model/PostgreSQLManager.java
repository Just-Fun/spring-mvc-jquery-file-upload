package com.hmkcode.spring.mvc.model;

import com.hmkcode.spring.mvc.data.FileMeta;
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

    public PostgreSQLManager() {
        connect();
    }

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

    private Connection getConnection() {
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

    public void insert(String fileName, InputStream inputStream, long size) {
        connection = getConnection(); // TODO MOVE TO CONSTRUCTOR
        String query = "INSERT INTO files (name, file, status, session) values (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            connection.setAutoCommit(false);
            pstmt.setString(1, fileName);
            pstmt.setBinaryStream(2, inputStream, (int) size);
            pstmt.setString(3, "upload");
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public InputStream selectFile(int id) {
        InputStream is = null;
        String query = String.format("SELECT file FROM files where id='%d'", id);
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
               is = resultSet.getBinaryStream(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return is;
    }
}
