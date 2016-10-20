package com.hmkcode.spring.mvc.model;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Serzh on 10/7/16.
 */
public interface DatabaseManager {

    void connect(String database, String userName, String password);

    void insertFile(String fileName, InputStream inputStream, long session);

    void insertResult(long session, Map<String, Integer> map);

    InputStream selectFileById(int id);

    List<Integer> selectIdBySession(long session);

    LinkedHashMap<String, Integer> getMapFromResultById(int id) throws Exception;

    LinkedHashMap<String, Integer> getMapFromResultBySession(long session) throws Exception;

    void createDatabase(String database);

    void createTable(String query);

    void dropDatabase(String database);
}
