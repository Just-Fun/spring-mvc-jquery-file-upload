package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;
import com.hmkcode.spring.mvc.result.JsonDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Serzh on 10/12/16.
 */
public class Service {
    private static List<Map<String, Integer>> maps = new ArrayList<>();
    static Map<String, Integer> map;
    static Map<String, Integer> result;
    static PostgreSQLManager manager;

    public static void main(String[] args) throws IOException {
        String result = new Service().run(1476441073232L);
        System.out.println(result);
    }

    public String run(long session) {
        manager = new PostgreSQLManager();
        List<Integer> selectIdFilesFromSession = manager.selectFiles(session);

        selectFileById(selectIdFilesFromSession);

        checkMaps();

        return resultToJson();
    }

    private static void selectFileById(List<Integer> selectIdFilesFromSession) {
        for (Integer id : selectIdFilesFromSession) {
            InputStream inputStream = manager.selectFile(id);
            createMapFromLines(inputStream);
        }
    }

    private static String resultToJson() {
        JsonDocument jsonDocument = new JsonDocument(result);
        return jsonDocument.toString();
    }

    private static void checkMaps() {
        if (maps.size() > 1) {
            result = concatMaps(maps);
        } else {
            result = maps.get(0);
        }
    }

    //TODO optimized algorithm
    public static Map<String, Integer> concatMaps(List<Map<String, Integer>> maps) {
        Map<String, Integer> result = maps.get(0);

        for (int i = 1; i < maps.size(); i++) {
            Map<String, Integer> mapConcat = maps.get(i);
            concatTwoMaps(result, mapConcat);
        }
        return result;
    }

    private static Map<String, Integer> concatTwoMaps(Map<String, Integer> result, Map<String, Integer> map2) {
        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            String line = entry.getKey();
            if (result.containsKey(line)) {
                result.put(line, result.get(line) + entry.getValue());
            } else {
                result.put(line, entry.getValue());
            }
        }
        return result;
    }

    private static void addLinesToMap(String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    private static void createMapFromLines(InputStream is) {
        map = new HashMap<>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    addLinesToMap(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        maps.add(map);
    }
}