package com.hmkcode.spring.mvc.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.*;
import java.sql.*;
import java.util.*;

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
            throw new RuntimeException("Some trouble with loading properties: " + e.getCause());
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

    public void insert(String fileName, InputStream inputStream, long session) {
        String query = "INSERT INTO files (name, file, status, session) values (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            connection.setAutoCommit(false);
            pstmt.setString(1, fileName);
            pstmt.setBinaryStream(2, inputStream);
            pstmt.setString(3, "upload"); // TODO if not use - delete
            pstmt.setLong(4, session);
            pstmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void insertResult(long session, Map<String, Integer> map) {

        String query = "INSERT INTO results (session,result) VALUES (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(bos);
            output.writeObject(map);

            output.flush();
            output.close();

            byte[] data = bos.toByteArray();
            ps.setLong(1, session);
            ps.setObject(2, data);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
    public void insert(String tableName, Map<String, Object> columnData) {
        StringJoiner tableNames = new StringJoiner(", ");
        StringJoiner values = new StringJoiner("', '", "'", "'");
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            tableNames.add(pair.getKey());
            values.add(pair.getValue().toString());
        }
        template.update(String.format("INSERT INTO public.%s(%s) values (%s)",
                tableName, tableNames.toString(), values.toString()));
    }


    public InputStream selectFileById(int id) {
        return template.queryForObject(String.format("SELECT file FROM files where id='%d'", id),
                (rs, rowNum) -> rs.getBinaryStream("file"));
     /*   InputStream is = null;
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
        return is;*/
    }

//    @Override
    public int getTableSize(String tableName) {
        return template.queryForObject(String.format("SELECT COUNT(*) FROM public.%s", tableName), Integer.class);
    }

    public List<Integer> selectIdBySession(long session) {
        return new LinkedList<>(template.query(String.format("SELECT id FROM files WHERE session='%d'", session),
                (rs, rowNum) -> rs.getInt("id")
        ));
       /* List<Integer> is = new LinkedList<>();
        String query = String.format("SELECT id FROM files WHERE session='%d'", session);
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                is.add(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return is;*/
    }

    public LinkedHashMap<String, Integer> getMapFromResultById(int id) throws Exception {
        LinkedHashMap<String, Integer> mc = null;
        String query = String.format("SELECT result FROM results where id='%d'", id);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    ByteArrayInputStream bais = new ByteArrayInputStream(rs.getBytes(1));
                    ObjectInputStream ins = new ObjectInputStream(bais);
                    mc = (LinkedHashMap) ins.readObject();
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mc;
        }
    }

    public LinkedHashMap<String, Integer> getMapFromResultBySession(long session) throws Exception {
        LinkedHashMap<String, Integer> mc = null;
        String query = String.format("SELECT result FROM results where session='%d'", session);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    ByteArrayInputStream bais = new ByteArrayInputStream(rs.getBytes(1));
                    ObjectInputStream ins = new ObjectInputStream(bais);
                    mc = (LinkedHashMap) ins.readObject();
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mc;
        }
    }
}
