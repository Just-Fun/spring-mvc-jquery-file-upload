package com.hmkcode.spring.mvc.model;

import com.hmkcode.spring.mvc.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by Serzh on 10/7/16.
 */
/*@Component("PostgreSQLManager")
@Scope(value = "prototype")*/
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
    private static String host;
    private static String port;

    private static String database;
    private static String userName;
    private static String password;

    private Connection connection;
    private JdbcTemplate template;

    public PostgreSQLManager() {
//        loadProperties();
        connect();
    }

    private static void loadProperties() {
        Utils utils = new Utils();
        host = utils.getHost();
        port = utils.getPort();
        database = utils.getDatabase();
        userName = utils.getUserName();
        password = utils.getPassword();
    }

    private void connect() {
        closeOpenedConnection();
        getConnection();
    }

    @Override
    public void connect(String database, String userName, String password) {
        if (userName != null && password != null) {
            this.userName = userName;
            this.password = password;
        }
        this.database = database;
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
//                connection = null;
//                template = null;
            } catch (SQLException e) {
                throw new DatabaseManagerException(ERROR, e);
            }
        }
    }

    @Override
    public void insertFile(String fileName, InputStream inputStream, long session) {
        String query = "INSERT INTO files (name, file, session) values (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            connection.setAutoCommit(false);
            pstmt.setString(1, fileName);
            pstmt.setBinaryStream(2, inputStream);
            pstmt.setLong(3, session);
            pstmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void insertResult(long session, Map<String, Integer> map) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream output = new ObjectOutputStream(bos);
            output.writeObject(map);

            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace(); // TODO all printStackTrace change with...
        }
        byte[] data = bos.toByteArray();

        String query = "INSERT INTO results (session,result) VALUES (?,?)";
        template.update(query, session, data);
    }

    @Override
    public InputStream selectFileById(int id) {
        String query = String.format("SELECT file FROM files where id='%d'", id);
        return template.queryForObject(query,
                (rs, rowNum) -> rs.getBinaryStream("file"));
    }

    @Override
    public List<Integer> selectIdBySession(long session) {
        String query = String.format("SELECT id FROM files WHERE session='%d'", session);
        return new LinkedList<>(template.query(query,
                (rs, rowNum) -> rs.getInt("id")));
    }

    @Override
    public LinkedHashMap<String, Integer> getMapFromResultById(int id) throws Exception {
        LinkedHashMap<String, Integer> result = null;
        String query = String.format("SELECT result FROM results where id='%d'", id);

        return getMap(result, query);
    }

    @Override
    public LinkedHashMap<String, Integer> getMapFromResultBySession(long session) throws Exception {
        LinkedHashMap<String, Integer> result = null;
        String query = String.format("SELECT result FROM results where session='%d'", session);
        return getMap(result, query);
    }

    private LinkedHashMap<String, Integer> getMap(LinkedHashMap<String, Integer> result, String query) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(rs.getBytes(1));
                    ObjectInputStream ins = new ObjectInputStream(inputStream);
                    result = (LinkedHashMap) ins.readObject();
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }

    @Override
    public void createDatabase(String database) {
        template.execute(String.format("CREATE DATABASE %s", database));
    }

    @Override
    public void createTable(String query) {
        template.execute(String.format("CREATE TABLE IF NOT EXISTS %s", query));
    }

    @Override
    public void dropDatabase(String database) {
        template.execute(String.format("DROP DATABASE IF EXISTS %s", database));
    }
}
